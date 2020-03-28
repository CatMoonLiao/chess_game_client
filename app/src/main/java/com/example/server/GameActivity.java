package com.example.server;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    GridView gv;
    ArrayList<Chess> Chesslist=new ArrayList<Chess>();
    Adapter adapter;
    int usertype=-1;
    int otherusertype=-1;
    boolean status=false;
    TextView score;
    int temp;
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://usermsg
                    usertype=Integer.valueOf((String) msg.obj);
                    System.out.println("Client get usertype:"+usertype);
                    setUser();
                    if(usertype==1)
                        status=true;
                    break;
                case 1://position
                    temp=Integer.valueOf((String)msg.obj);
                    System.out.println("Client get position:"+temp);
                    if(!status){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Chess c=(Chess) gv.getItemAtPosition(temp);
                                c.type=otherusertype;
                                Chesslist.get(temp).IfSameInLine(Chesslist, temp, otherusertype, true);
                                Chesslist.set(temp,c);
                                adapter.notifyDataSetChanged();
                                setScore();
                                status=true;
                                showTurn();
                            }
                        });
                    }
                    break;
                case 2:
                    temp=Integer.valueOf((String)msg.obj);
                    if(temp==1000)
                        endGame(1);
                    else
                        endGame(0);
                    break;
            }
        }
    };
    SocketClient socket;
    public  void showTurn(){
        Toast.makeText(this, "You turn!", Toast.LENGTH_SHORT).show();

    }
    public void setScore(){
        int black=0,white=0;
        boolean end=false;
        for(Chess c:Chesslist){
            if(c.type==1)
                white++;
            else if(c.type==0)
                black++;
            else {
                if (c.CanClick(Chesslist, Chesslist.indexOf(c), usertype)) {
                    end = true;
                }
            }
        }
        if(!end)
            socket.sendMsg(100);
        else if(usertype==0){
            score.setText(black+" : "+white);
        }else
            score.setText(white+" : "+black);

    }
    public void setUser(){
        if(usertype==1) {
            status = true;
            otherusertype=0;
            TextView tv=findViewById(R.id.showtype);
            tv.setText("你是白方");
        }else if(usertype==0){
            otherusertype=1;
            TextView tv=findViewById(R.id.showtype);
            tv.setText("你是黑方");
        }
    }

    public void endGame(int a){
        if(a==1)
            Toast.makeText(this, "You Win :D", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "You Lose :(", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(GameActivity.this,MainActivity.class);
        startActivity(intent);
        GameActivity.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);
        init_game();
        socket=new SocketClient(handler);
        gv=(GridView) findViewById(R.id.grid);
        score=findViewById(R.id.score);
        adapter=new Adapter(this,Chesslist);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chess temp =(Chess) gv.getItemAtPosition(position);
                if(!status) ;
                else if(temp.type!=-1) ;
                else if(temp.CanClick(Chesslist,position,usertype)){
                    temp.type=usertype;
                    Chesslist.get(position).IfSameInLine(Chesslist, position, usertype, true);
                    Chesslist.set(position,temp);
                    adapter.notifyDataSetChanged();
                    socket.sendMsg(position);
                    setScore();
                    status=false;
                }
            }
        });
    }
    private void init_game(){
        for(int i=0;i<6;i++){
            for(int j=0;j<6;j++){
                Chess chess =new Chess(-1);
                chess.x=i;
                chess.y=j;
                if((i==2||i==3) && i==j) {
                    chess.type = 0;
                }else if((i==3&&j==2)||(i==2&&j==3)) {
                    chess.type = 1;
                }
                Chesslist.add(chess);
            }
        }
    }

}

