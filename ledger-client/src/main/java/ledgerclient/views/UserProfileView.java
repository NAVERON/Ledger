package ledgerclient.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import ledgerclient.service.UserAcountService;
import ledgerclient.utils.HttpClientUtils;
import ledgerclient.utils.LogicController;
import model.user.UserAndPermissionDTO;

/**
 * 自定义组件实现 用户icon 名称等其他内容的显示 
 * @author eron
 *
 */
public class UserProfileView extends FlowPane {
    
    private static final Logger log = LoggerFactory.getLogger(UserProfileView.class);
    private LogicController controller;
    private String currentUserToken;
    
    /**
     * 使用 {{}} 完全初始化组件 这种初始化方法也不错 就是看起来不方便 
     */
    private ImageView userICON = new ImageView(getClass().getResource("/assets/images/a.png").toExternalForm());
    private Text userName = new Text("USER_NAME");
    private Label roleDescription = new Label("DEFAULT");
    private BooleanProperty isLogin = new SimpleBooleanProperty();  // 表明当前是否已经登录 
    
    private UserAndPermissionDTO user = UserAndPermissionDTO.createBuilder().build();
    
    public UserProfileView() {
        this.initComponent();
    }
    public UserProfileView(LogicController controller) {
        this.bindController(controller);
        this.initComponent();
    }
    public UserProfileView(UserAndPermissionDTO user) {
        this.user = user;
        this.initComponent();
    }
    
    private void initComponent() {
        // 初始化组件 组件布局设计 
        this.setAlignment(Pos.TOP_CENTER);
        this.getChildren().addAll(userICON, userName, roleDescription);
        
        this.backgroundProperty().bind(
            Bindings.when(hoverProperty())
            .then(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)))
            .otherwise(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)))
        );
        
        this.setOnMouseClicked(event -> {
            log.info("点击组件--> {}", "dede");
        });
        
        this.userICON.setFitWidth(100);
        this.userICON.setFitHeight(100);
        Circle clip = new Circle(50, 50, 50);
        this.userICON.setClip(clip);
        
        this.userName.setLineSpacing(10);
        this.roleDescription.setBorder(new Border(
            new BorderStroke(
                Color.BLACK, 
                BorderStrokeStyle.SOLID, 
                CornerRadii.EMPTY, 
                BorderWidths.DEFAULT
            )
        ));
        
        ImageView loginICON = new ImageView(getClass().getResource("/assets/images/drink.png").toExternalForm());
        loginICON.setFitWidth(100);
        loginICON.setFitHeight(100);
        
        this.userICON.setOnMouseClicked(event -> {
            if(event.getClickCount() >= 2 || event.getButton() != MouseButton.PRIMARY) {
                return;
            }
            
            if(this.isLogin.getValue()) {
                log.info("当前已经登陆, 点击后是登出");
                Alert alert = new Alert(AlertType.CONFIRMATION, "Lohout ?", ButtonType.YES, ButtonType.CANCEL);
                alert.showAndWait();
                if(alert.getResult() == ButtonType.YES) {
                    // 确认登出 
                    this.isLogin.setValue(!this.isLogin.getValue());
                    log.info("当前用户登出  --> {}", this.currentUserToken);
                    this.currentUserToken = null;
                    this.user = null;
                    UserAcountService.currentUserID = -1L;
                }
                return;
            }
            
            LoginStage loginView = new LoginStage(this);
            loginView.initOwner(this.controller.getPrimaryStage());
            loginView.showAndWait();
            
            // this.isLogin.setValue(!this.isLogin.getValue());  // 登录成功后设置 
        });
        this.isLogin.addListener((obs, oleState, newState) -> {
            log.info("登录状态发生变化");

            this.userICON.setImage(
                newState // this.isLogin.getValue() 
                ? new Image(getClass().getResource("/assets/images/hashtag.png").toExternalForm()) 
                : new Image(getClass().getResource("/assets/images/a.png").toExternalForm()) 
            );
        });
        
        this.setHgap(50);
    }
    
    public void setUser(UserAndPermissionDTO user) {
        log.info("设置用户信息");
        this.userName.setText(user.getUserName());
        this.user = user;
        
        UserAcountService.currentUserID = user.getId();  // 设置全局userid 
    }
    
    public UserAndPermissionDTO getUser() {
        return this.user;
    }
    
    public void bindController(LogicController controller) {  // 双向绑定 
        this.controller = controller;
        this.controller.linkUserProfile(this);
    }
    
    public String doLogin(String userName, String password) {
        this.currentUserToken = UserAcountService.getInstance().doLogin(userName, password);
        if(this.currentUserToken == null || this.currentUserToken.isBlank()) {
            throw new IllegalArgumentException("token 是null或者是空的");
        }
        
        this.setLoginStatus(Boolean.TRUE);
        return this.currentUserToken;
    }
    
    public void setLoginStatus(Boolean loginStatus) {
        this.isLogin.setValue(loginStatus);
    }
    
    
}




