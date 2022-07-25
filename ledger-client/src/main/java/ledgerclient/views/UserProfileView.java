package ledgerclient.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

/**
 * 自定义组件实现 用户icon 名称等其他内容的显示 
 * @author eron
 *
 */
public class UserProfileView extends FlowPane {

    private ImageView icon;
    private Text userName;
    private Label roleDescription;
    private Button doAction;
    
    public UserProfileView() {
        this.getChildren().addAll(icon, userName, roleDescription, doAction);
    }
    
    private void initComponent() {
        // 初始化组件 组件布局设计 
    }
    
    public void addDoActionButtonEventHandler(EventHandler<ActionEvent> event) {
        this.doAction.setOnAction(event);
    }
    
}


