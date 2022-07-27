package ledgerclient.views;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
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

    private ImageView icon = new ImageView(
                new Image(getClass().getResource("/assets/images/1.png").toExternalForm(), true)
            );
    private Text userName = new Text("USER_NAME");
    private Label roleDescription = new Label("DEFAULT");
    private Button doAction = new Button("LOGIN_LOGOUT");
    
    private UserAndPermissionDTO user = UserAndPermissionDTO.createBuilder().build();
    
    public UserProfileView() {
        
        this.initComponent();
    }
    
    private void initComponent() {
        this.setAlignment(Pos.TOP_CENTER);
        
        icon.setFitWidth(100);
        icon.setFitHeight(100);
        
        // 初始化组件 组件布局设计 
        this.getChildren().addAll(icon, userName, roleDescription, doAction);
        
        this.styleProperty().bind(
            Bindings
            .when(hoverProperty())
            .then(new SimpleStringProperty("-fx-background-color: #43CD80;"))
            .otherwise(new SimpleStringProperty("-fx-background-color: #F4F4F4;"))
        );
        
        this.setOnMouseClicked(e -> {
            log.info("点击组件--> {}", this.componentID);
        });
    }
    
    public void addDoActionButtonEventHandler(EventHandler<ActionEvent> event) {
        this.doAction.setOnAction(event);
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
    
}




