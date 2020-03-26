import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//@author 冯懿
//@Time 2018-1-11
public class BabyMis extends Application{
    static int i = 0;
    private TextField num = new TextField();
    private TextField adress = new TextField();
    private TextField postcode = new TextField();
    private TextField mother = new TextField();
    private TextField father = new TextField();
    private TextField sex = new TextField();
    private TextField weight = new TextField();
    private TextField nurse = new TextField();
    private TextField birth = new TextField();

    Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {//声明异常
        BorderPane root = new BorderPane();
        Image image = new Image("baby.png");//获取图片
        ImageView imageview = new ImageView(image);
        imageview.setFitHeight(380);
        imageview.setFitWidth(284);
        root.getChildren().add(imageview);

        num.setPromptText("婴儿编号，必填");
        mother.setPromptText("必填");
        sex.setPromptText("必填");
        nurse.setPromptText("0100-0112,必填");
        birth.setPromptText("格式：2018-1-1");
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.setTop(menuBar);
        // 菜单栏
        Menu search = new Menu("查询");
        MenuItem checkAll = new MenuItem("查询全部");
        MenuItem check = new MenuItem("按条件查询");
        MenuItem chekLow = new MenuItem("查询超低体重");
        search.getItems().addAll(checkAll, check, chekLow);

        Menu add = new Menu("新增");
        add.getItems().add(new MenuItem("新增"));

        Menu update = new Menu("修改");
        update.getItems().add(new MenuItem("修改"));

        Menu delete = new Menu("删除");
        delete.getItems().add(new MenuItem("删除"));

        Menu exit = new Menu("退出");
        exit.getItems().add(new MenuItem("退出"));

        menuBar.getMenus().addAll(search, add, update, delete, exit);

        checkAll.setOnAction(e -> {
            try {
                queryall();
            }
            catch (FileNotFoundException ex) {//捕获异常
                Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        check.setOnAction(e -> queryWindow());

        chekLow.setOnAction(e -> {
            try {
                lowweight();
            }
            catch (FileNotFoundException ex) {//捕获异常
                Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        add.setOnAction(e -> addWindow());

        update.setOnAction(e -> updateWindow());

        delete.setOnAction(e -> deleteWindow());

        exit.setOnAction(e -> stage.close());

        Scene scene = new Scene(root, 284, 380);//定义面板大小
        stage.setTitle("婴儿出生信息管理系统");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args){
        Application.launch(args);
    }

    private void queryall() throws FileNotFoundException {//显示所有婴儿信息
        File Fl = new File("Number.txt");
        File fl = new File("Baby.txt");
        try (Scanner input = new Scanner(Fl)) {
            i = input.nextInt();
        }

        GridPane pane = new GridPane();
        pane.setHgap(20);
        pane.setVgap(10);

        pane.add(new Label("婴儿编号") , 1, 0);
        pane.add(new Label("家庭住址") , 2, 0);
        pane.add(new Label("邮政编码"), 3, 0);
        pane.add(new Label("母亲"), 4, 0);
        pane.add(new Label("父亲"), 5, 0);
        pane.add(new Label("性别"), 6, 0);
        pane.add(new Label("体重(kg)"), 7, 0);
        pane.add(new Label("护理人员"), 8, 0);
        pane.add(new Label("出生日期    "), 9, 0);

        Baby[] baby = new Baby[i];
        Text[][] text = new Text[i][9];
        int j;
        try(Scanner Input = new Scanner(fl)){
            for(j =0; j < i;j++){
                baby[j] = new Baby(Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.nextFloat(), Input.next(), Input.next());
                text[j][0] = new Text(baby[j].getNum());
                text[j][1] = new Text(baby[j].getAdress());
                text[j][2] = new Text(baby[j].getPostcode());
                text[j][3] = new Text(baby[j].getMother());
                text[j][4] = new Text(baby[j].getFather());
                text[j][5] = new Text(baby[j].getSex());
                text[j][6] = new Text(""+baby[j].getWeight());
                text[j][7] = new Text(baby[j].getNurse());
                text[j][8] = new Text(baby[j].getBirth());

                pane.add(text[j][0], 1, j + 1);
                pane.add(text[j][1], 2, j + 1);
                pane.add(text[j][2], 3, j + 1);
                pane.add(text[j][3], 4, j + 1);
                pane.add(text[j][4], 5, j + 1);
                pane.add(text[j][5], 6, j + 1);
                pane.add(text[j][6], 7, j + 1);
                pane.add(text[j][7], 8, j + 1);
                pane.add(text[j][8], 9, j + 1);
            }
        }
        Button bt = new Button("返回");
        pane.add(bt, 5, j + 1);

        bt.setOnAction(e -> {
            try {
                start(stage);
            }
            catch (Exception ex) {
                Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Scene scene = new Scene(pane);
        stage.setTitle("全部婴儿信息");
        stage.setScene(scene);
    }

    private void queryWindow() {//查询窗体
        Text text1 = new Text(60, 60, "母亲:");
        mother.setLayoutX(130);
        mother.setLayoutY(40);
        Text text2 = new Text(60, 90, "性别:");
        sex.setLayoutX(130);
        sex.setLayoutY(70);
        Text text3 = new Text(60, 120, "出生日期:");
        birth.setLayoutX(130);
        birth.setLayoutY(100);
        mother.setPromptText("");
        sex.setPromptText("");

        Button btmom = new Button("查询");
        btmom.setLayoutX(330);
        btmom.setLayoutY(40);
        Button btsex = new Button("查询");
        btsex.setLayoutX(330);
        btsex.setLayoutY(70);
        Button btbirth = new Button("查询");
        btbirth.setLayoutX(330);
        btbirth.setLayoutY(100);


        Button bt2 = new Button("返回");
        bt2.setLayoutX(180);
        bt2.setLayoutY(165);
        Pane pane = new Pane();
        pane.getChildren().addAll(text1, text2, text3, mother, sex, birth, btmom, btsex, btbirth, bt2);

        Scene scene = new Scene(pane, 400, 230);
        stage.setTitle("条件查询");
        stage.setScene(scene);

        btmom.setOnAction(e -> {
            try {
                queryMother();
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btsex.setOnAction(e -> {
            try {
                querySex();
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btbirth.setOnAction(e -> {
            try {
                queryBirth();
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        bt2.setOnAction(e -> {
            try {
                start(stage);
            }
            catch (Exception ex) {
                Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void queryMother() throws FileNotFoundException {//查询操作
        File Fl = new File("Number.txt");
        File fl = new File("Baby.txt");
        try (Scanner input = new Scanner(Fl)) {
            i = input.nextInt();
        }

        String querymother = mother.getText();

        Baby[] baby = new Baby[i];
        int j;
        try(Scanner Input = new Scanner(fl)){
            for(j =0; j < i;j++){
                baby[j] = new Baby(Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.nextFloat(), Input.next(), Input.next());
                if(querymother.compareTo(baby[j].getMother()) == 0){
                    Text text1 = new Text(100, 50, "婴儿编号:\t" + baby[j].getNum());
                    Text text2 = new Text(100, 80, "家庭住址:\t" + baby[j].getAdress());
                    Text text3 = new Text(100, 110, "邮政编码:\t" + baby[j].getPostcode());
                    Text text4 = new Text(100, 140, "  母亲:\t" + baby[j].getMother());
                    Text text5 = new Text(100, 170, "  父亲:\t" + baby[j].getFather());
                    Text text6 = new Text(100, 200, "  性别:\t" + baby[j].getSex());
                    Text text7 = new Text(100, 230, "体重(kg):\t" + baby[j].getWeight());
                    Text text8 = new Text(100, 260, "护理人员:\t" + baby[j].getNurse());
                    Text text9 = new Text(100, 290, "出生日期:\t" + baby[j].getBirth());

                    Button bt1 = new Button("返回");
                    bt1.setLayoutX(120);
                    bt1.setLayoutY(320);
                    Pane pane = new Pane();
                    pane.getChildren().addAll(text1, text2, text3, text4, text5, text6, text7, text8, text9, bt1);
                    Scene scene = new Scene(pane, 280, 370);
                    stage.setTitle("查询结果");
                    stage.setScene(scene);
                    bt1.setOnAction(e -> queryWindow());
                }
            }

            if(j == i ){
                Text text = new Text(150, 100, "未找到!");
                text.setFill(Color.RED);
                Button bt1 = new Button("返回");
                bt1.setLayoutX(150);
                bt1.setLayoutY(130);
                Pane pane = new Pane();
                pane.getChildren().addAll(text, bt1);

                Scene scene = new Scene(pane, 350, 250);
                stage.setScene(scene);

                bt1.setOnAction(e -> queryWindow());
            }
        }
    }

    private void querySex() throws FileNotFoundException {//查询操作
        File Fl = new File("Number.txt");
        File fl = new File("Baby.txt");
        try (Scanner input = new Scanner(Fl)) {
            i = input.nextInt();
        }

        GridPane pane = new GridPane();
        pane.setHgap(20);
        pane.setVgap(10);

        pane.add(new Label("婴儿编号") , 1, 0);
        pane.add(new Label("家庭住址") , 2, 0);
        pane.add(new Label("邮政编码"), 3, 0);
        pane.add(new Label("母亲"), 4, 0);
        pane.add(new Label("父亲"), 5, 0);
        pane.add(new Label("性别"), 6, 0);
        pane.add(new Label("体重(kg)"), 7, 0);
        pane.add(new Label("护理人员"), 8, 0);
        pane.add(new Label("出生日期    "), 9, 0);

        String querysex = sex.getText();
        Baby[] baby = new Baby[i];
        Text[][] text = new Text[i][9];
        int j;
        int k=0;
        try(Scanner Input = new Scanner(fl)){
            for(j =0; j < i;j++){
                baby[j] = new Baby(Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.nextFloat(), Input.next(), Input.next());
                if(querysex.compareTo(baby[j].getSex()) == 0){
                    text[j][0] = new Text(baby[j].getNum());
                    text[j][1] = new Text(baby[j].getAdress());
                    text[j][2] = new Text(baby[j].getPostcode());
                    text[j][3] = new Text(baby[j].getMother());
                    text[j][4] = new Text(baby[j].getFather());
                    text[j][5] = new Text(baby[j].getSex());
                    text[j][6] = new Text(""+baby[j].getWeight());
                    text[j][7] = new Text(baby[j].getNurse());
                    text[j][8] = new Text(baby[j].getBirth());

                    pane.add(text[j][0], 1, k + 1);
                    pane.add(text[j][1], 2, k + 1);
                    pane.add(text[j][2], 3, k + 1);
                    pane.add(text[j][3], 4, k + 1);
                    pane.add(text[j][4], 5, k + 1);
                    pane.add(text[j][5], 6, k + 1);
                    pane.add(text[j][6], 7, k + 1);
                    pane.add(text[j][7], 8, k + 1);
                    pane.add(text[j][8], 9, k + 1);
                    k++;
                }

                if(j==i-1 && k!=0){
                    Button bt = new Button("返回");
                    pane.add(bt, 4, k + 1);
                    bt.setOnAction(e -> queryWindow());
                    Scene scene = new Scene(pane);
                    stage.setTitle("查询结果");
                    stage.setScene(scene);
                    break;}
            }

            if(j == i){
                Text text1 = new Text(150, 100, "未找到!");
                text1.setFill(Color.RED);
                Button bt1 = new Button("返回");
                bt1.setLayoutX(150);
                bt1.setLayoutY(130);
                Pane pane1 = new Pane();
                pane1.getChildren().addAll(text1, bt1);
                Scene scene = new Scene(pane1, 350, 250);
                stage.setTitle("未找到！");
                stage.setScene(scene);

                bt1.setOnAction(e -> queryWindow());

            }
        }
    }

    private void queryBirth() throws FileNotFoundException {//查询操作
        File Fl = new File("Number.txt");
        File fl = new File("Baby.txt");
        try (Scanner input = new Scanner(Fl)) {
            i = input.nextInt();
        }

        GridPane pane = new GridPane();
        pane.setHgap(20);
        pane.setVgap(10);

        pane.add(new Label("婴儿编号") , 1, 0);
        pane.add(new Label("家庭住址") , 2, 0);
        pane.add(new Label("邮政编码"), 3, 0);
        pane.add(new Label("母亲"), 4, 0);
        pane.add(new Label("父亲"), 5, 0);
        pane.add(new Label("性别"), 6, 0);
        pane.add(new Label("体重(kg)"), 7, 0);
        pane.add(new Label("护理人员"), 8, 0);
        pane.add(new Label("出生日期    "), 9, 0);

        String querybirth = birth.getText();
        Baby[] baby = new Baby[i];
        Text[][] text = new Text[i][9];
        int j;
        int k=0;
        try(Scanner Input = new Scanner(fl)){
            for(j =0; j < i;j++){
                baby[j] = new Baby(Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.nextFloat(), Input.next(), Input.next());
                if(querybirth.compareTo(baby[j].getBirth()) == 0){
                    text[j][0] = new Text(baby[j].getNum());
                    text[j][1] = new Text(baby[j].getAdress());
                    text[j][2] = new Text(baby[j].getPostcode());
                    text[j][3] = new Text(baby[j].getMother());
                    text[j][4] = new Text(baby[j].getFather());
                    text[j][5] = new Text(baby[j].getSex());
                    text[j][6] = new Text(""+baby[j].getWeight());
                    text[j][7] = new Text(baby[j].getNurse());
                    text[j][8] = new Text(baby[j].getBirth());

                    pane.add(text[j][0], 1, k + 1);
                    pane.add(text[j][1], 2, k + 1);
                    pane.add(text[j][2], 3, k + 1);
                    pane.add(text[j][3], 4, k + 1);
                    pane.add(text[j][4], 5, k + 1);
                    pane.add(text[j][5], 6, k + 1);
                    pane.add(text[j][6], 7, k + 1);
                    pane.add(text[j][7], 8, k + 1);
                    pane.add(text[j][8], 9, k + 1);
                    k++;
                }

                if(j==i-1 && k!=0){
                    Button bt = new Button("返回");
                    pane.add(bt, 4, k + 1);
                    bt.setOnAction(e -> queryWindow());
                    Scene scene = new Scene(pane);
                    stage.setTitle("查询结果");
                    stage.setScene(scene);
                    break;}
            }

            if(j == i ){
                Text text1 = new Text(150, 100, "未找到!");
                text1.setFill(Color.RED);
                Button bt1 = new Button("返回");
                bt1.setLayoutX(150);
                bt1.setLayoutY(130);
                Pane pane1 = new Pane();
                pane1.getChildren().addAll(text1, bt1);
                Scene scene = new Scene(pane1, 350, 250);
                stage.setTitle("未找到！");
                stage.setScene(scene);
                bt1.setOnAction(e -> queryWindow());
            }
        }
    }

    private void lowweight() throws FileNotFoundException {//查询操作
        File Fl = new File("Number.txt");
        File fl = new File("Baby.txt");
        try (Scanner input = new Scanner(Fl)) {
            i = input.nextInt();
        }

        GridPane pane = new GridPane();
        pane.setHgap(20);
        pane.setVgap(10);

        pane.add(new Label("婴儿编号") , 1, 0);
        pane.add(new Label("家庭住址") , 2, 0);
        pane.add(new Label("邮政编码"), 3, 0);
        pane.add(new Label("母亲"), 4, 0);
        pane.add(new Label("父亲"), 5, 0);
        pane.add(new Label("性别"), 6, 0);
        pane.add(new Label("体重"), 7, 0);
        pane.add(new Label("护理人员"), 8, 0);
        pane.add(new Label("出生日期    "), 9, 0);

        Baby[] baby = new Baby[i];
        Text[][] text = new Text[i][9];
        int j;
        int k=0;
        try(Scanner Input = new Scanner(fl)){
            for(j =0; j < i;j++){
                baby[j] = new Baby(Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.nextFloat(), Input.next(), Input.next());
                if(baby[j].getWeight()<2.5){
                    text[j][0] = new Text(baby[j].getNum());
                    text[j][1] = new Text(baby[j].getAdress());
                    text[j][2] = new Text(baby[j].getPostcode());
                    text[j][3] = new Text(baby[j].getMother());
                    text[j][4] = new Text(baby[j].getFather());
                    text[j][5] = new Text(baby[j].getSex());
                    text[j][6] = new Text(""+baby[j].getWeight());
                    text[j][7] = new Text(baby[j].getNurse());
                    text[j][8] = new Text(baby[j].getBirth());

                    pane.add(text[j][0], 1, k + 1);
                    pane.add(text[j][1], 2, k + 1);
                    pane.add(text[j][2], 3, k + 1);
                    pane.add(text[j][3], 4, k + 1);
                    pane.add(text[j][4], 5, k + 1);
                    pane.add(text[j][5], 6, k + 1);
                    pane.add(text[j][6], 7, k + 1);
                    pane.add(text[j][7], 8, k + 1);
                    pane.add(text[j][8], 9, k + 1);
                    k++;
                }

                if(j==i-1 && k!=0){
                    Button bt = new Button("返回");
                    pane.add(bt, 4, k + 1);
                    bt.setOnAction(e -> {
                        try {
                            start(stage);
                        }
                        catch (Exception ex) {
                            Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    Scene scene = new Scene(pane);
                    stage.setTitle("超低体重婴儿名单");
                    stage.setScene(scene);
                    break;}
            }

            if(j == i ){
                Text text1 = new Text(110, 100, "未找到超低体重的婴儿!");
                text1.setFill(Color.RED);
                Button bt1 = new Button("返回");
                bt1.setLayoutX(150);
                bt1.setLayoutY(130);
                Pane pane1 = new Pane();
                pane1.getChildren().addAll(text1, bt1);
                Scene scene = new Scene(pane1, 350, 250);
                stage.setTitle("未找到！");
                stage.setScene(scene);

                bt1.setOnAction(e -> {
                    try {
                        start(stage);
                    }
                    catch (Exception ex) {
                        Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        }
    }

    private void addWindow() {//新增窗口
        Text text1 = new Text(70, 60, "婴儿编号:\t" );
        Text text2 = new Text(70, 90, "家庭住址:\t" );
        Text text3 = new Text(70, 120, "邮政编码:\t");
        Text text4 = new Text(70, 150, "母亲:\t");
        Text text5 = new Text(70, 180, "父亲:\t");
        Text text6 = new Text(70, 210, "性别:\t");
        Text text7 = new Text(70, 240, "体重(kg):\t");
        Text text8 = new Text(70, 270, "护理人员:\t");
        Text text9 = new Text(70, 300, "出生日期:\t");

        num.setLayoutX(140);num.setLayoutY(40);
        adress.setLayoutX(140);adress.setLayoutY(70);
        postcode.setLayoutX(140);postcode.setLayoutY(100);
        mother.setLayoutX(140);mother.setLayoutY(130);
        father.setLayoutX(140);father.setLayoutY(160);
        sex.setLayoutX(140);sex.setLayoutY(190);
        weight.setLayoutX(140);weight.setLayoutY(220);
        nurse.setLayoutX(140);nurse.setLayoutY(250);
        birth.setLayoutX(140);birth.setLayoutY(280);

        Button bt1 = new Button("确定");
        Button bt2 = new Button("返回");
        bt1.setLayoutX(100);
        bt1.setLayoutY(320);
        bt2.setLayoutX(220);
        bt2.setLayoutY(320);
        Pane pane = new Pane();
        pane.getChildren().addAll(text1, text2, text3, text4, text5, text6, text7, text8, text9,
                num,adress,postcode,father,mother,sex,weight,nurse,birth,bt1, bt2);
        Scene scene = new Scene(pane, 370, 360);
        stage.setScene(scene);

        stage.setTitle("新增");
        stage.setScene(scene);

        bt1.setOnAction(e -> {
            try {if(mother.getText().isEmpty()||sex.getText().isEmpty()||nurse.getText().isEmpty())
                failWindow();
                else if (mother.getText().length()>12||father.getText().length()>12||adress.getText().length()>128)
                    failWindow();
                else if (!nurse.getText().matches("^01[0-1][0-2]"))
                    failWindow();
                else if(!(postcode.getText().matches("^\\d{0,6}$")|| postcode.getText().isEmpty()))
                    failWindow();
                else if(!(sex.getText().equals("男")||sex.getText().equals("女")))
                    failWindow();
                else if(!(weight.getText().matches("^\\d+(\\.\\d)?") || weight.getText().isEmpty()))
                failWindow();
                else addbaby();
            }
            catch (Exception ex) {
                Logger.getLogger(Baby.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        bt2.setOnAction(e -> {
            try {
                start(stage);
            }
            catch (Exception ex) {
                Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void addbaby() throws Exception {//添加数据操作
        if(adress.getText().isEmpty())
            adress.setText("*");
        else if(postcode.getText().isEmpty())
            postcode.setText("*");
        else if(father.getText().isEmpty())
            father.setText("*");
        else if(weight.getText().isEmpty())
            weight.setText("*");
        else if(birth.getText().isEmpty())
            birth.setText("*");

        Baby baby = new Baby(num.getText(), adress.getText(), postcode.getText(), mother.getText(), father.getText(), sex.getText(), Float.parseFloat(weight.getText()), nurse.getText(), birth.getText());//使用自定义的类暂时储存数据

        File file = new File("Number.txt");
        try (Scanner input = new Scanner(file)) {
            i = input.nextInt();
        }
        catch(Exception e){
            i = 0;
        }

        try (FileWriter out = new FileWriter("Baby.txt",true)) {
            out.write("\t");
            out.write(baby.getNum() + "\t");
            out.write(baby.getAdress() + "\t");
            out.write(baby.getPostcode()+ "\t");
            out.write(baby.getMother() + "\t");
            out.write(baby.getFather() + "\t");
            out.write(baby.getSex() + "\t");
            out.write(baby.getWeight() + "\t");
            out.write(baby.getNurse() + "\t");
            out.write(baby.getBirth() + "\n");
        }

        i++;
        try(PrintWriter output = new PrintWriter(file)){
            output.print(i);
        }

        start(stage);
    }

    private void updateWindow() {//修改选择窗口
        Pane pane = new Pane();
        Text text = new Text(100, 100.0, "请输入你要修改的婴儿编号:");
        num.setLayoutX(100);
        num.setLayoutY(120);

        Button bt = new Button("确认");
        Button bt2 = new Button("返回");
        bt.setLayoutX(120);
        bt.setLayoutY(165);
        bt2.setLayoutX(190);
        bt2.setLayoutY(165);
        pane.getChildren().addAll(text, num, bt,bt2);//画面重合时，添加的先后顺序决定了那个类型覆盖在另一个类型上

        Scene scene = new Scene(pane, 360, 260);
        stage.setTitle("修改");
        stage.setScene(scene);

        bt.setOnAction(e -> {
            try {
                if(num.getText().isEmpty())
                {failWindow();}
                updateconfirm();
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        bt2.setOnAction(e -> {
            try {
                start(stage);
            }
            catch (Exception ex) {
                Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void updateconfirm() throws FileNotFoundException {//修改信息确认
        File Fl = new File("Number.txt");
        File fl = new File("Baby.txt");
        try (Scanner input = new Scanner(Fl)) {
            i = input.nextInt();
        }

        String Number = num.getText();

        Baby[] baby = new Baby[i];
        int j;
        try(Scanner Input = new Scanner(fl)){
            for(j =0; j < i;j++){
                baby[j] = new Baby(Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.nextFloat(), Input.next(), Input.next());
                if(Number.compareTo(baby[j].getNum()) == 0){
                    Text text1 = new Text(70, 60, "婴儿编号:\t" );
                    Text text2 = new Text(70, 90, "家庭住址:\t" );
                    Text text3 = new Text(70, 120, "邮政编码:\t");
                    Text text4 = new Text(70, 150, "母亲:\t");
                    Text text5 = new Text(70, 180, "父亲:\t");
                    Text text6 = new Text(70, 210, "性别:\t");
                    Text text7 = new Text(70, 240, "体重(kg):\t");
                    Text text8 = new Text(70, 270, "护理人员:\t");
                    Text text9 = new Text(70, 300, "出生日期:\t");

                    TextField newnum = new TextField(baby[j].getNum());
                    TextField newadress = new TextField(baby[j].getAdress());
                    TextField newpostcode = new TextField(baby[j].getPostcode());
                    TextField newmother = new TextField(baby[j].getMother());
                    TextField newfather = new TextField(baby[j].getFather());
                    TextField newsex = new TextField(baby[j].getSex());
                    TextField newweight = new TextField(""+baby[j].getWeight());
                    TextField newnurse = new TextField(baby[j].getNurse());
                    TextField newbirth = new TextField(baby[j].getBirth());
                    newnum.setPromptText("输入婴儿编号，必填");
                    newmother.setPromptText("必填");
                    newsex.setPromptText("必填");
                    newnurse.setPromptText("0100-0112,必填");
                    newbirth.setPromptText("如2018-1-1");
                    newnum.setLayoutX(140);newnum.setLayoutY(40);
                    newadress.setLayoutX(140);newadress.setLayoutY(70);
                    newpostcode.setLayoutX(140);newpostcode.setLayoutY(100);
                    newmother.setLayoutX(140);newmother.setLayoutY(130);
                    newfather.setLayoutX(140);newfather.setLayoutY(160);
                    newsex.setLayoutX(140);newsex.setLayoutY(190);
                    newweight.setLayoutX(140);newweight.setLayoutY(220);
                    newnurse.setLayoutX(140);newnurse.setLayoutY(250);
                    newbirth.setLayoutX(140);newbirth.setLayoutY(280);

                    Button bt1 = new Button("修改");
                    Button bt2 = new Button("返回");
                    bt1.setLayoutX(100);
                    bt1.setLayoutY(320);
                    bt2.setLayoutX(220);
                    bt2.setLayoutY(320);
                    Pane pane = new Pane();
                    pane.getChildren().addAll(text1, text2, text3, text4, text5, text6, text7, text8, text9,
                            newnum,newadress,newpostcode,newfather,newmother,newsex,newweight,newnurse,newbirth,bt1, bt2);
                    Scene scene = new Scene(pane, 370, 360);
                    stage.setScene(scene);
                    stage.setTitle("修改");

                    bt1.setOnAction(e -> {
                        try {
                            if(newnum.getText().isEmpty()||newmother.getText().isEmpty()||newsex.getText().isEmpty()||newnurse.getText().isEmpty())
                                failWindow();
                            else if (newmother.getText().length()>12||newfather.getText().length()>12||newadress.getText().length()>128)
                                failWindow();
                            else if (!newnurse.getText().matches("^01[0-1][0-2]"))
                                failWindow();
                            else if(!(newpostcode.getText().matches("^\\d{0,6}$")|| newpostcode.getText().isEmpty()))
                                failWindow();
                            else if(!(newsex.getText().equals("男")||newsex.getText().equals("女")))
                                failWindow();
                            else if(!(newweight.getText().matches("^\\d+(\\.\\d)?") || newweight.getText().isEmpty()))
                                failWindow();
                            else{
                                if(newadress.getText().isEmpty())
                                    newadress.setText("*");
                                else if(newpostcode.getText().isEmpty())
                                    newpostcode.setText("*");
                                else if(newfather.getText().isEmpty())
                                    newfather.setText("*");
                                else if(newweight.getText().isEmpty())
                                    newweight.setText("*");
                                else if(newbirth.getText().isEmpty())
                                    newbirth.setText("*");
                                updatebaby(newnum, newadress, newpostcode, newmother, newfather, newsex, newweight, newnurse, newbirth);
                                sucessWindow();
                            }

                        }
                        catch (FileNotFoundException ex) {
                            Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    bt2.setOnAction(e -> {
                        try {
                            start(stage);
                        }
                        catch (Exception ex) {
                            Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    break;
                }
            }

            if(j == i ){
                Text text = new Text(120, 100, "不存在该婴儿数据！");
                text.setFill(Color.RED);
                Button bt1 = new Button("重试");
                Button bt2 = new Button("返回");
                bt1.setLayoutX(100);
                bt1.setLayoutY(130);
                bt2.setLayoutX(200);
                bt2.setLayoutY(130);
                Pane pane = new Pane();
                pane.getChildren().addAll(text, bt1, bt2);

                Scene scene = new Scene(pane, 350, 250);
                stage.setScene(scene);

                bt1.setOnAction(e -> updateWindow());
                bt2.setOnAction(e -> {
                    try {
                        start(stage);
                    }
                    catch (Exception ex) {
                        Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        }
    }

    private void updatebaby(TextField newnum,TextField newadress, TextField newpostcode, TextField newmother, TextField newfather,
                       TextField newsex, TextField newweight, TextField newnurse, TextField newbirth) throws  Exception {//
        File Fl = new File("Number.txt");
        File fl = new File("Baby.txt");
        try (Scanner input = new Scanner(Fl)) {
            i = input.nextInt();
        }

        String Number = num.getText();

        Baby[] baby = new Baby[i];
        try(Scanner Input = new Scanner(fl)){
            for(int j =0; j < i;j++){
                baby[j] = new Baby(Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.nextFloat(), Input.next(), Input.next());
                if(Number.compareTo(baby[j].getNum()) == 0){
                    baby[j] = new Baby(newnum.getText(), newadress.getText(), newpostcode.getText(), newmother.getText(), newfather.getText(), newsex.getText(), Float.parseFloat(newweight.getText()), newnurse.getText(),newbirth.getText());
                }
            }
        }

        try(PrintWriter output = new PrintWriter(Fl)){
            output.print(i);
        }

        if(i != 0){
            try (PrintWriter output = new PrintWriter(fl)) {
                for(int j = 0;j < i;j ++){
                    output.print(baby[j].getNum() + "\t");
                    output.print(baby[j].getAdress() + "\t");
                    output.print(baby[j].getPostcode() + "\t");
                    output.print(baby[j].getMother() + "\t");
                    output.print(baby[j].getFather() + "\t");
                    output.print(baby[j].getSex() + "\t");
                    output.print(baby[j].getWeight() + "\t");
                    output.print(baby[j].getNurse() + "\t");
                    output.println(baby[j].getBirth());
                }
            }
        }
        start(stage);
    }

    private void deleteWindow() {//删除窗口
        Text text = new Text(120, 100, "请输入要删除的婴儿编号:");
        num.setLayoutX(115);
        num.setLayoutY(120);
        Button bt = new Button("确认");
        bt.setLayoutX(130);
        bt.setLayoutY(165);
        Button bt2 = new Button("返回");
        bt2.setLayoutX(210);
        bt2.setLayoutY(165);
        Pane pane = new Pane();
        pane.getChildren().addAll(text, num, bt,bt2);

        Scene scene = new Scene(pane, 390, 280);
        stage.setTitle("删除");
        stage.setScene(scene);

        bt.setOnAction(e -> {
            try {
                if(num.getText().isEmpty())
                {failWindow();}
                deleteconfirm();
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        bt2.setOnAction(e -> {
            try {
                start(stage);
            }
            catch (Exception ex) {
                Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void deleteconfirm() throws FileNotFoundException{
        File Fl = new File("Number.txt");
        File fl = new File("Baby.txt");
        try (Scanner input = new Scanner(Fl)) {
            i = input.nextInt();
        }

        String Number = num.getText();

        Baby[] baby = new Baby[i];
        int j;
        try(Scanner Input = new Scanner(fl)){
            for(j =0; j < i;j++){
                baby[j] = new Baby(Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.nextFloat(), Input.next(), Input.next());
                if(Number.compareTo(baby[j].getNum()) == 0){
                    Text text1 = new Text(100, 40, "婴儿编号:\t" + baby[j].getNum());
                    Text text2 = new Text(100, 70, "家庭住址:\t" + baby[j].getAdress());
                    Text text3 = new Text(100, 100, "邮政编码:\t" + baby[j].getPostcode());
                    Text text4 = new Text(100, 130, "   母亲:\t" + baby[j].getMother());
                    Text text5 = new Text(100, 160, "   父亲:\t" + baby[j].getFather());
                    Text text6 = new Text(100, 190, "   性别:\t" + baby[j].getSex());
                    Text text7 = new Text(100, 220, "   体重:\t" + baby[j].getWeight());
                    Text text8 = new Text(100, 250, "护理人员:\t" + baby[j].getNurse());
                    Text text9 = new Text(100, 280, "出生日期:\t" + baby[j].getBirth());
                    Text text = new Text(120, 310, "确认删除该婴儿信息吗？");
                    text.setFill(Color.RED);

                    Button bt1 = new Button("删除");
                    Button bt2 = new Button("返回");
                    bt1.setLayoutX(110);
                    bt1.setLayoutY(330);
                    bt2.setLayoutX(200);
                    bt2.setLayoutY(330);

                    Pane pane = new Pane();
                    pane.getChildren().addAll(text1, text2, text3, text4, text5, text6, text7, text8 , text9, text, bt1, bt2);
                    Scene scene = new Scene(pane, 350, 380);
                    stage.setScene(scene);

                    bt1.setOnAction(e -> {
                        try {
                            deletebaby();
                            sucessWindow();
                        }
                        catch (FileNotFoundException ex) {
                            Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        catch (Exception ex) {
                            Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    bt2.setOnAction(e -> deleteWindow());
                    break;
                }
            }

            if(j == i ){
                Text text = new Text(150, 100, "未找到!");
                text.setFill(Color.RED);
                Button bt1 = new Button("重试");
                Button bt2 = new Button("退出");
                bt1.setLayoutX(100);
                bt1.setLayoutY(130);
                bt2.setLayoutX(200);
                bt2.setLayoutY(130);
                Pane pane = new Pane();
                pane.getChildren().addAll(text, bt1, bt2);

                Scene scene = new Scene(pane, 350, 250);
                stage.setTitle("删除确认");
                stage.setScene(scene);

                bt1.setOnAction(e -> deleteWindow());
                bt2.setOnAction(e -> {
                    try {
                        start(stage);
                    }
                    catch (Exception ex) {
                        Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        }
    }

    private void deletebaby() throws Exception {//删除功能
        File Fl = new File("Number.txt");
        File fl = new File("Baby.txt");
        try (Scanner input = new Scanner(Fl)) {
            i = input.nextInt();
        }

        String Number = num.getText();

        Baby[] baby = new Baby[i];
        try(Scanner Input = new Scanner(fl)){
            for(int j =0; j < i;j++){
                baby[j] = new Baby(Input.next(),Input.next(), Input.next(), Input.next(), Input.next(), Input.next(), Input.nextFloat(), Input.next(), Input.next());
                if(Number.compareTo(baby[j].getNum()) == 0){//确定删除则用下一组数据，将要删除的数据覆盖
                    i --;
                    j --;
                }
            }
        }

        try(PrintWriter output = new PrintWriter(Fl)){
            output.print(i);
        }

        if(i == 0){//如果删除后的婴儿数量为0，则不输入任何数据，使文件的内容为空
            try (PrintWriter output = new PrintWriter(fl)) {
            }
        }
        else{
            try (PrintWriter output = new PrintWriter(fl)) {
                for(int j = 0;j < i;j ++){
                    output.print(baby[j].getNum() + "\t");
                    output.print(baby[j].getAdress() + "\t");
                    output.print(baby[j].getPostcode() + "\t");
                    output.print(baby[j].getFather() + "\t");
                    output.print(baby[j].getMother() + "\t");
                    output.print(baby[j].getSex() + "\t");
                    output.print(baby[j].getWeight() + "\t");
                    output.print(baby[j].getNurse() + "\t");
                    output.println(baby[j].getBirth());
                }
            }
        }
        start(stage);
    }

    private void sucessWindow() {//成功提示
        Text text = new Text(150, 100, "操作成功!");
        text.setFill(Color.RED);
        Button bt = new Button("返回");
        bt.setLayoutX(150);
        bt.setLayoutY(130);
        Pane pane = new Pane();
        pane.getChildren().addAll(text, bt);

        Scene scene = new Scene(pane, 350, 250);
        stage.setScene(scene);

        bt.setOnAction(e -> {
            try {
                start(stage);
            }
            catch (Exception ex) {
                Logger.getLogger(BabyMis.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void failWindow() {//弹窗
        Stage stage2 = new Stage();
        Text text = new Text(30, 50, "输入不合法,请检查!");
        text.setFill(Color.RED);
        Button bt = new Button("确定");
        bt.setLayoutX(55);
        bt.setLayoutY(70);
        Pane pane = new Pane();
        pane.getChildren().addAll(text, bt);
        Scene scene = new Scene(pane, 150, 120);
        stage2.setScene(scene);
        stage2.setTitle("警告");
        stage2.show();
        bt.setOnAction(e -> stage2.close());
    }

}

