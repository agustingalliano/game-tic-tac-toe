package galliano.android.ejercicio_juego;

import java.util.Random;

public class Match {
    public final int severity;
    public int player;
    private final int [] boxes;
    private final int [][] COMBINATIONS ={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public Match (int severity){
        this.severity=severity;
        player=1;
        boxes = new int[9];
        for(int i =0; i<9 ; i++){
            boxes[i]=0;
        }
    }

    public int twoInLine (int player_in_turn){
        int box=-1;
        int amount=0;
        for (int[] array : COMBINATIONS) {
            for (int pos : array) {
                if(boxes[pos]==player_in_turn)amount++;
                if(boxes[pos]==0) box=pos;
            }
            if(amount==2 && box!=-1) return box;
            box=-1;
            amount=0;
        }
        return -1;
    }

    public int ia (){
        Random random_box = new Random();
        int box = twoInLine(2);
        if(box!=-1) return box;

        if(severity>0){
            box=twoInLine(1);
            if(box!=-1) return box;
        }

        if(severity==2){
            if(boxes[4]==0) return 4;
            if(boxes[0]==0) return 0;
            if(boxes[2]==0) return 2;
            if(boxes[6]==0) return 6;
            if(boxes[8]==0) return 8;
        }

        return random_box.nextInt(9);
    }

    public int turn (){
        boolean tie = true;
        boolean last_movement = true;

        for (int[] array : COMBINATIONS) {
            for (int pos : array) {
                if(boxes[pos]!=player) last_movement=false;
                if (boxes[pos] == 0) tie=false;
            }
            if(last_movement) return player;
            last_movement=true;
        }

        if(tie){
            return 3;
        }

        player++;
        if(player>2){
            player=1;
        }

        return 0;
    }

    public boolean Taken(int box){
        if(boxes[box]==0){
            boxes[box]=player;
            return false;
        }
        return true;
    }

}
