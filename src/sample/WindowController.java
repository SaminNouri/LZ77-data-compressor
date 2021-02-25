package sample;

import controller.FolderContentReader;
import controller.PathRecognizer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Stack;

public class WindowController implements Initializable {



    private static ArrayList<File> files=new ArrayList<>();

    private static ArrayList<ImageView> images=new ArrayList<>();

    private static ArrayList<Text> texts=new ArrayList<>();

    private static Stack<ArrayList<File>> history=new Stack<>();

    private static File res;

    @FXML
    private TextField input;

    @FXML
    private TextField otput;

    @FXML
    private TextField password;

    @FXML
    private Button zip;

    @FXML
    private Button back;

    @FXML
    private Button unzip;

    @FXML
    private ImageView im1;

    @FXML
    private ImageView im2;

    @FXML
    private ImageView im3;

    @FXML
    private ImageView im4;

    @FXML
    private ImageView im5;

    @FXML
    private ImageView im6;

    @FXML
    private ImageView im7;

    @FXML
    private ImageView im8;

    @FXML
    private ImageView im9;

    @FXML
    private ImageView im10;

    @FXML
    private ImageView im11;

    @FXML
    private ImageView im12;

    @FXML
    private ImageView im13;

    @FXML
    private ImageView im14;

    @FXML
    private ImageView im15;

    @FXML
    private ImageView im16;

    @FXML
    private Text t1;

    @FXML
    private Text t2;

    @FXML
    private Text t3;

    @FXML
    private Text t4;

    @FXML
    private Text t5;

    @FXML
    private Text t6;

    @FXML
    private Text t7;

    @FXML
    private Text t8;

    @FXML
    private Text t9;

    @FXML
    private Text t10;

    @FXML
    private Text t11;

    @FXML
    private Text t12;

    @FXML
    private Text t13;

    @FXML
    private Text t14;

    @FXML
    private Text t15;

    @FXML
    private Text t16;

    @FXML
    private TextField b;

    @FXML
    private TextField v;

    @FXML
    private Text passwordCheck;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setImageArrays();
        setTextArrays();
        passwordCheck.setText("");

        try {
            setImages(files.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        zip.setOnAction((event) -> {
            handleZipCommand(input.getText(), otput.getText(),password.getText(),Integer.valueOf(b.getText()),Integer.valueOf(v.getText()));
            System.out.println("Button clicked");
            passwordCheck.setText("");
            files=new ArrayList<>();
        });

        unzip.setOnAction((event) -> {
            passwordCheck.setText("");
            handleUnZipCommand(input.getText(), otput.getText(),password.getText());
            try {
                setImages(files.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Button clicked");
        });

        back.setOnAction((event) -> {
            passwordCheck.setText("");
            if(history.size()>=1) {
                files = history.pop();
                try {
                    setImages(files.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Button clicked");
        });








    }

    private void setTextArrays(){
        texts.add(t1);
        texts.add(t2);
        texts.add(t3);
        texts.add(t4);

        texts.add(t5);
        texts.add(t6);
        texts.add(t7);
        texts.add(t8);

        texts.add(t9);
        texts.add(t10);
        texts.add(t11);
        texts.add(t12);

        texts.add(t13);
        texts.add(t14);
        texts.add(t15);
        texts.add(t16);

    }


    private void setImageArrays(){
        images.add(im1);
        images.add(im2);
        images.add(im3);
        images.add(im4);

        images.add(im5);
        images.add(im6);
        images.add(im7);
        images.add(im8);

        images.add(im9);
        images.add(im10);
        images.add(im11);
        images.add(im12);

        images.add(im13);
        images.add(im14);
        images.add(im15);
        images.add(im16);

    }


    public  void setImages(int nums) throws Exception{
        FileInputStream inputstream = new FileInputStream("src/sample/images/1.jpg");
        Image imageDir = new Image(inputstream);

        inputstream = new FileInputStream("src/sample/images/2.png");
        Image imageFile = new Image(inputstream);
        int c=0;
        for ( c=0;c<nums;c++) {
            res=files.get(c);
            System.out.println("c is:"+c+" "+files+" "+res);
            if( res.getName().contains(".DS_Store"))
                continue;
            ImageView imageView=images.get(c);
            imageView.setVisible(true);
            imageView.setImage(imageDir);
            texts.get(c).setText(files.get(c).getName());
            if(res.isFile() || res.getName().contains(".DS_Store")) {
                imageView.setImage(imageFile);
                imageView.setOnMouseClicked(event -> {
                        }
                );
                continue;
            }
            imageView.setOnMouseClicked(event -> {
                res=files.get(images.indexOf(imageView));
                history.add(copy(files));
                files=new ArrayList<>();
                for (File fileEntry : res.listFiles()) {
                    if(!files.contains(fileEntry) && !res.getName().contains(".DS_Store") && !fileEntry.getName().contains(".DS_Store"))
                      files.add(fileEntry);
                }
                System.out.println("filesfinal:"+files+" "+res.getName()+" ");
                        try {
                            setImages(files.size());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                );

        }
        for (c=c;c<images.size();c++) {
            texts.get(c).setText("");
            ImageView imageView = images.get(c);
            imageView.setVisible(false);
            imageView.setOnMouseClicked(event -> {
                    }
            );
        }

    }

    private static ArrayList<File> copy(ArrayList<File> org){

        ArrayList<File> ans=new ArrayList<>();
        for (File f:org)
        {
            ans.add(f);
        }

        return ans;

    }



    public void handleZipCommand(String input,String output,String pass,int b,int v){
        File theDir = new File(input);
        try {
            PathRecognizer.writeDataToFile(theDir,output,b,v,pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleUnZipCommand(String input,String output,String pass){
        FolderContentReader folderContentReader=new FolderContentReader(input);
        try {
            String s=folderContentReader.readAllTheFile(output,pass);
            if(s==null) {
                passwordCheck.setText("wrong password");
                return;
            }
            File f=new File(output);
            if (!f.exists() ){
                f.mkdirs();
            }
            files.add(f);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}