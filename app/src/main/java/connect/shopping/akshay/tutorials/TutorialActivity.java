package connect.shopping.akshay.tutorials;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class TutorialActivity extends AppCompatActivity {

    private ImageButton btnC, btnCPlus, btnPython, btnJava, btnSql, btnPhp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        btnC = (ImageButton)findViewById(R.id.imgC);
        btnCPlus = (ImageButton)findViewById(R.id.imgCpluplus);
        btnJava = (ImageButton)findViewById(R.id.imgJava);
        btnPython = (ImageButton)findViewById(R.id.imgPython);
        btnSql = (ImageButton)findViewById(R.id.imgSQL);
        btnPhp = (ImageButton)findViewById(R.id.imgPhp);


        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TutorialActivity.this,PageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type",1);
//                bundle.putParcelableArrayList("vedios", vedio_list);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });

        btnCPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialActivity.this,PageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type",2);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        btnJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TutorialActivity.this,PageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type",4);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        btnPython.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialActivity.this,PageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type",6);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        btnPhp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialActivity.this,PageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type",3);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        btnSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialActivity.this,PageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type",5);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }
}
