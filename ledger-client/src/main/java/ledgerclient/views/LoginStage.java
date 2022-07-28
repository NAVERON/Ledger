package ledgerclient.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ledgerclient.utils.HttpClientUtils;
import model.user.UserAndPermissionDTO;
import utils.JWTUtils;



/**
 * 用来登录操作的界面 
 * @author wangy
 *
 */
public class LoginStage extends Stage {

    private static final Logger log = LoggerFactory.getLogger(LoginStage.class);
    private UserProfileView userProfile;
    
    public LoginStage(UserProfileView userProfile) {
        this.linkController(userProfile);
        
        this.initComponent();
    }
    public LoginStage() {
        this.initComponent();
    }
    
    private void initComponent() {
        Scene scene = new Scene(this.createContent());
        
        this.initModality(Modality.WINDOW_MODAL);
        this.initStyle(StageStyle.UNDECORATED);
        this.setScene(scene);
    }
    
    private Parent createContent() {
        GridPane root = new GridPane();
        
        Label userNameLabel = new Label("用户名称");
        TextField userNameTextField = new TextField();
        userNameTextField.setPromptText("user name");
        Label passwordLabel = new Label("用户密码");
        TextField passwordTextField = new TextField();
        passwordTextField.setPromptText("password");
        
        root.add(userNameLabel, 0, 0);
        root.add(userNameTextField, 1, 0);
        root.add(passwordLabel, 0, 1);
        root.add(passwordTextField, 1, 1);
        
        Button cancel = new Button("取消");
        cancel.setOnAction(e -> {
            log.info("登陆界面不做任何动作");
            this.closeLoginStage();
        });
        Button ok = new Button("确定登录");
        ok.setOnAction(e -> {
            // 异步登录 
            log.info("执行登录动作, 并等待返回");
            String token = this.userProfile.doLogin(userNameTextField.getText().trim(), passwordTextField.getText().trim());
            log.info("获取到的token --> {}", token);
            UserAndPermissionDTO user = JWTUtils.getUser(token);
            log.info("获取到的user对象 --> {}", user);
            this.userProfile.setUser(user);
            HttpClientUtils.setToken(token);
            
            this.closeLoginStage();
        });
        root.add(cancel, 2, 2);
        root.add(ok, 3, 2);
        
        root.setPadding(new Insets(20));
        root.setVgap(20);
        root.setHgap(20);
        root.setBackground(new Background(
                new BackgroundFill(
                    new Color(0, 0.4, 0.4, 0.5), 
                    CornerRadii.EMPTY, 
                    Insets.EMPTY
                )
            )
        );
        root.setBorder(new Border(
                new BorderStroke(
                    Color.BLACK, 
                    BorderStrokeStyle.SOLID, 
                    CornerRadii.EMPTY, 
                    BorderWidths.DEFAULT
                )
            )
        );
        
        return root;
    }
    
    public void showAndWait() {
        this.show();
    }
    
    public void closeLoginStage() {
        this.close();
    }

    public void linkController(UserProfileView userProfile) {
        this.userProfile = userProfile;
    }
    
    
}
