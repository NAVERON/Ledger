package ledgerclient.views;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import ledgerclient.utils.IControlBinding;
import ledgerclient.utils.IEventBinding;
import model.user.UserAndPermissionDTO;

/**
 * 自定义组件实现 用户icon 名称等其他内容的显示 
 * @author eron
 *
 */
public class UserProfileView extends FlowPane implements IEventBinding {
    
    private static final Logger log = LoggerFactory.getLogger(UserProfileView.class);

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
    public UserProfileView(UserAndPermissionDTO user) {
        this.user = user;
        this.initComponent();
    }
    
    private void initComponent() {
        // 初始化组件 组件布局设计 
        this.setAlignment(Pos.TOP_CENTER);
        this.getChildren().addAll(userICON, userName, roleDescription);
        
        this.styleProperty().bind(
            Bindings
            .when(hoverProperty())
            .then(new SimpleStringProperty("-fx-background-color: #43CD80;"))
            .otherwise(new SimpleStringProperty("-fx-background-color: #F4F4F4;"))
        );
        
        this.setOnMouseClicked(event -> {
            log.info("点击组件--> {}", this.componentID);
        });
        
        this.userICON.setFitWidth(100);
        this.userICON.setFitHeight(100);
        Circle clip = new Circle(50, 50, 50);
        this.userICON.setClip(clip);
        
        ImageView loginICON = new ImageView(getClass().getResource("/assets/images/drink.png").toExternalForm());
        loginICON.setFitWidth(100);
        loginICON.setFitHeight(100);
        this.userICON.setOnMouseClicked(event -> {
            if(event.getClickCount() >= 2 || event.getButton() != MouseButton.PRIMARY) {
                return;
            }
            
            this.isLogin.setValue(!this.isLogin.getValue());
        });
        this.isLogin.addListener((obs, oleState, newState) -> {
            log.info("登录状态发生变化");
            if(this.controller == null) {
                return;
            }
            this.userICON.setImage(
                this.isLogin.getValue() 
                ? new Image(getClass().getResource("/assets/images/hashtag.png").toExternalForm()) 
                : new Image(getClass().getResource("/assets/images/a.png").toExternalForm()) 
            );
        });
    }
    
    public void setUser(UserAndPermissionDTO user) {
        this.user = user;
    }
    public UserAndPermissionDTO getUser() {
        return this.user;
    }

    /**
     * 关于中心控制 消息传递的思路实现 
     */
    private String componentID;
    private IControlBinding controller;

    @Override 
    public String getComponentID() {
        if(this.controller == null || this.componentID == null) {
            throw new IllegalAccessError("No Binding Controller Error");
        }
        return this.componentID;
    }

    @Override 
    public void binding(IControlBinding controller) {
        this.componentID = UUID.randomUUID().toString();
        this.controller = controller;
        this.controller.bindACK(this.componentID, this);  // 这种调用会造成死循环 调用震荡 
    }

    @Override 
    public void unBinding() {
        this.controller = null;
        this.componentID = null;
    }
    @Override
    public void executeCommand(String from, String to, String command) {
        // TODO Auto-generated method stub
        
    }
    
}




