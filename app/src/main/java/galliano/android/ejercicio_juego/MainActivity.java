package galliano.android.ejercicio_juego;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends Activity {

    private int players=0;
    private int [] BOXES;
    private Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BOXES = new int[9];
        BOXES[0]=R.id.a1;
        BOXES[1]=R.id.a2;
        BOXES[2]=R.id.a3;
        BOXES[3]=R.id.b1;
        BOXES[4]=R.id.b2;
        BOXES[5]=R.id.b3;
        BOXES[6]=R.id.c1;
        BOXES[7]=R.id.c2;
        BOXES[8]=R.id.c3;
        Button btn1 = (Button) findViewById(R.id.aplayer);
        Button btn2 = (Button) findViewById(R.id.two_players);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPlay(v);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPlay(v);
            }
        });

        ImageView imageView;
        for(int i=0;i<9;i++){
            imageView = (ImageView) findViewById(BOXES[i]);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    touch(v);
                }
            });
        }
    }

    private void toPlay(View view){
        ImageView image;
        int severity=0;
        for(int i=0; i<9;i++){
           image = (ImageView) findViewById(BOXES[i]);
           image.setImageResource(R.drawable.box);
        }

        players=1;
        RadioGroup difficulty = (RadioGroup) findViewById(R.id.difficulty);
        int id = difficulty.getCheckedRadioButtonId();
        if(view.getId()==R.id.aplayer){
            severity=toPlayOnly(id);
        }
        if(view.getId()==R.id.two_players){
            players=2;
        }

        match = new Match (severity);

        ((Button) findViewById(R.id.aplayer)).setEnabled(false);
        ((Button) findViewById(R.id.two_players)).setEnabled(false);
        ((RadioButton)findViewById(R.id.easy)).setEnabled(false);
        ((RadioButton)findViewById(R.id.normal)).setEnabled(false);
        ((RadioButton)findViewById(R.id.difficult)).setEnabled(false);
        difficulty.setAlpha(0);

    }

    private int toPlayOnly(int id){
        if(id==R.id.normal){
            return  1;
        }
        if(id==R.id.difficult){
            return 2;
        }
        return 0;
    }

    public void touch(View myView){
        if(match==null){
            return;
        }
        if(players==1){
            touchOnly(myView);
        }
        if(players==2){
            touchTogether(myView);
        }

    }

    private void touchTogether(View myView){
        int box=0;
        for(int i=0;i<9;i++){
            if(BOXES[i]==myView.getId()){
                box=i;
                break;
            }
        }
        if(match.Taken(box)){
            return;
        }
        mark(box);
        int result = match.turn();
        if(result>0){
            methodFinish(result);
        }
    }

    private void touchOnly(View myView){
        int box=0;
        for(int i=0;i<9;i++){
            if(BOXES[i]==myView.getId()){
                box=i;
                break;
            }
        }
        if(match.Taken(box)){
            return;
        }

        mark(box);
        int result = match.turn();
        if(result>0){
            methodFinish(result);
            return;
        }

        box = match.ia();
        while(match.Taken(box)){
            box = match.ia();
        }
        mark(box);
        result=match.turn();
        if(result>0){
            methodFinish(result);
        }
    }

    private void methodFinish (int result){
        Resources resources = getResources();
        String message=".";
        if(result==1) message=getResources().getString(R.string.circle_win);
        if(result==2) message=getResources().getString(R.string.crosses_win);
        if(result==3) message=getResources().getString(R.string.tie);
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
        match=null;
        ((Button) findViewById(R.id.aplayer)).setEnabled(true);
        ((Button) findViewById(R.id.two_players)).setEnabled(true);
        ((RadioButton)findViewById(R.id.easy)).setEnabled(true);
        ((RadioButton)findViewById(R.id.normal)).setEnabled(true);
        ((RadioButton)findViewById(R.id.difficult)).setEnabled(true);
        ((RadioGroup) findViewById(R.id.difficulty)).setAlpha(1);
    }

    private void mark (int box){
        ImageView image;
        image = (ImageView) findViewById(BOXES[box]);
        if(match.player==1){
            image.setImageResource(R.drawable.circle);
        }else{
            image.setImageResource(R.drawable.cross);
        }
    }

}