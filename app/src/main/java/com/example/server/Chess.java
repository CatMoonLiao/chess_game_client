package com.example.server;

import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

public class Chess {
    int x;
    int y;
    int type=-1;

    public Chess(int usertype){
        type=usertype;
        int img=ChecksetImage();
    }

    public int ChecksetImage(){
        if(type==0){
            return R.drawable.blackchess;
        }else if(type==1){
            return R.drawable.whitechess;
        }else
            return 0;
    }

    //check if there is chess in nearby
    //then check if change chess
    public boolean CanClick(ArrayList<Chess> list,int position,int usertype){
        if(IfNearbyExist(position,list,usertype)){
            if(IfSameInLine(list,position,usertype,false)) {
                return true;
            }
        }
        return false;
    }

    public boolean IfSameInLine(ArrayList<Chess> list,int position,int usertype,boolean change){
        int x,y;
        x=position/6;
        y=position%6;
        boolean ans=false;

        //右下
        for(int i=1;i+x<6&&i+y<6;i++){
            if(i==1 && list.get(position+i*7).type==-1)
                break;
            if(list.get(position+i*7).type==usertype){
                if(i==1){
                    break;
                }
                else if(change){
                    for(i=i-1;i>0;i--){
                        Chess temp=list.get(position+i*7);
                        if(temp.type==-1)
                            continue;
                        temp.type=usertype;
                        list.set(position+i*7,temp);
                    }
                }
                ans=true;
                break;
            }
        }
        //下
        for(int i=1;i+x<6;i++){
            if(i==1 && list.get(position+i*6).type==-1)
                break;
            if(list.get(position+i*6).type==usertype) {
                if(i==1){
                    break;
                }
                else if(change){
                    for(i=i-1;i>0;i--){
                        Chess temp=list.get(position+i*6);
                        if(temp.type==-1)
                            continue;
                        temp.type=usertype;
                        list.set(position+i*6,temp);
                    }
                }
                ans=true;
                break;
            }
        }
        //左下
        for(int i=1;i+x<6&&y-i>=0;i++){
            if(i==1 && list.get(position+i*5).type==-1)
                break;
            if(list.get(position+i*5).type==usertype) {
                if(i==1){
                    break;
                }
                else if(change){
                    for(i=i-1;i>0;i--){
                        Chess temp=list.get(position+i*5);
                        if(temp.type==-1)
                            continue;
                        temp.type=usertype;
                        list.set(position+i*5,temp);
                    }
                }
                ans = true;
                break;
            }
        }
        //左
        for(int i=1;y-i>=0;i++){
            if(i==1 && list.get(position-i).type==-1)
                break;
            if(list.get(position-i).type==usertype) {
                if(i==1){
                    break;
                }
                else if(change){
                    for(i=i-1;i>0;i--){
                        Chess temp=list.get(position-i);
                        if(temp.type==-1)
                            continue;
                        temp.type=usertype;
                        list.set(position-i,temp);
                    }
                }
                ans=true;
                break;
            }
        }
        //左上
        for(int i=1;x-i>=0&&y-i>=0;i++){
            if(i==1 && list.get(position-i*7).type==-1)
                break;
            if(list.get(position-i*7).type==usertype){
                if(i==1){
                    break;
                }
                else if(change){
                    for(i=i-1;i>0;i--){
                        Chess temp=list.get(position-i*7);
                        if(temp.type==-1)
                            continue;
                        temp.type=usertype;
                        list.set(position-i*7,temp);
                    }
                }
                ans= true;
                break;
            }
        }
        //上
        for(int i=1;x-i>=0;i++){
            if(i==1 && list.get(position-i*6).type==-1)
                break;
            if(list.get(position-i*6).type==usertype) {
                if(i==1){
                    break;
                }
                else if(change){
                    for(i=i-1;i>0;i--){
                        Chess temp=list.get(position-i*6);
                        if(temp.type==-1)
                            continue;
                        temp.type=usertype;
                        list.set(position-i*6,temp);
                    }
                }
                ans= true;
                break;
            }
        }
        //右上
        for(int i=1;x-i>=0&&y+i<6;i++){
            if(i==1 && list.get(position-i*5).type==-1)
                break;
            if(list.get(position-i*5).type==usertype) {
                if(i==1){
                    break;
                }
                else if(change){
                    for(i=i-1;i>0;i--){
                        Chess temp=list.get(position-i*5);
                        if(temp.type==-1)
                            continue;
                        temp.type=usertype;
                        list.set(position-i*5,temp);
                    }
                }
                ans= true;
                break;
            }
        }
        //右
        for(int i=1;y+i<6;i++){
            if(i==1 && list.get(position+i).type==-1)
                break;
            if(list.get(position+i).type==usertype){
                if(i==1){
                    break;
                }
                else if(change){
                    for(i=i-1;i>0;i--){
                        Chess temp=list.get(position+i);
                        if(temp.type==-1)
                            continue;
                        temp.type=usertype;
                        list.set(position+i,temp);
                    }
                }
                ans= true;
                break;
            }
        }
        return ans;
    }

    public boolean IfNearbyExist(int position,ArrayList<Chess> list,int usertype){
        int positionX=position/6;
        int positionY=position%6;
        if(Ifexist(list,positionX+1,positionY+1,usertype)|| Ifexist(list,positionX+1, positionY,usertype)||
                Ifexist(list,positionX+1,positionY-1,usertype)||Ifexist(list,positionX,positionY+1,usertype)||
                Ifexist(list,positionX,positionY-1,usertype)||Ifexist(list,positionX-1,positionY+1,usertype)||
                Ifexist(list,positionX-1, positionY,usertype) || Ifexist(list,positionX-1,positionY-1,usertype)){
            return true;
        }else{
            return false;
        }
    }
    private boolean Ifexist(ArrayList<Chess> list,int x,int y,int usertype){
        if(x>=0 && x<6 && y>=0 && y<6){
            int position=x*6+y;
            if(list.get(position).type!=-1&&list.get(position).type!=usertype)
                return true;
            else
                return false;
        }
        else
            return false;
    }
}
