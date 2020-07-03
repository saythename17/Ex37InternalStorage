package com.icandothisallday2020.ex37internalstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=findViewById(R.id.et);
        tv=findViewById(R.id.tv);

    }
    public void clickSave(View view) {
        //파일에 저장할 데이터-et 에서 얻어오기
        String data=et.getText().toString();
        //Create Stream for save file in Internal Storage(내부메모리)
        //액티비티 내부메모리에 저장을 위해 OutputStream 을 여는 기능 메소드
        et.setText("");//EditText 글씨 지우기
        try {
            FileOutputStream fos=openFileOutput("data.txt",MODE_APPEND);
            //parameter:저장할 file 명,mode:덮어쓰기 or 이어쓰기
            //내부메모리는 기본적으로 외부에 노출X,경로를 알아도 외부접근불가
            //기본적으로 영구저장이나 앱에 종속적인 데이터:앱을 삭제하면 삭제됨
            //┌바이트 단위-> 문자단위로 바꿔서 가져오기
            PrintWriter writer=new PrintWriter(fos);//ByteStream->StringStream
            writer.println(data);//data 작성
            writer.flush();//남아있을지 모르는 데이터 밀어넣기
            writer.close();//무지개로드 닫기
        } catch (Exception e) {Toast.makeText(this,"ERROR",Toast.LENGTH_SHORT).show();}
        Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();

    }
    public void clickLoad(View view) {
        try {
            FileInputStream fis=openFileInput("data.txt");
            InputStreamReader isr=new InputStreamReader(fis);//byte->char 변환기
            BufferedReader reader=new BufferedReader(isr);//한문자(char)->한줄(String)
            StringBuffer buffer=new StringBuffer();//문자열 누적
            while(true){
                if((reader.readLine())==null) break;
                buffer.append(reader.readLine()+"\n");//br은 "\n"을 제외하고 한 줄 읽어옴
            }
            tv.setText(buffer/*.toString()생략가능*/);
        } catch (Exception e) {}
    }


}
