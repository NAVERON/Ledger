package ledgerclient.views;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import ledgerclient.utils.IControlBinding;
import ledgerclient.utils.IEventBinding;

/**
 * 左侧垂直菜单单元 
 * @author eron 
 *
 */
public class VerticalMenuItem extends HBox implements IEventBinding {
    
    private static final Logger log = LoggerFactory.getLogger(VerticalMenuItem.class);
    
    private ImageView icon = new ImageView(
                new Image(getClass().getResource("/assets/images/1.png").toExternalForm(), true)
            );
    private Text menuItemName = new Text("DEFAULT");
    private BooleanProperty selected = new SimpleBooleanProperty(); // 当前是否选中状态 绑定组件整体的样式 
    
    public VerticalMenuItem(String name) {
        
        this.initComponent();
    }
    
    private void initComponent() {
        this.setAlignment(Pos.CENTER_LEFT);
        
        icon.setFitWidth(100);
        icon.setFitHeight(100);
        
        this.getChildren().addAll(icon, menuItemName);
        
        this.styleProperty().bind(
            Bindings
            .when(hoverProperty())
            .then(new SimpleStringProperty("-fx-background-color: #43CD80;"))
            .otherwise(new SimpleStringProperty("-fx-background-color: #F4F4F4;"))
        );
        
        // 点击事件 改变 BooleanProperty 状态进而控制其他逻辑同步 
        this.setOnMouseClicked((event) -> {
            if(event.getClickCount() >= 2 || event.getButton() != MouseButton.PRIMARY) {
                return;
            }
            
            this.selected.set(!this.selected.getValue());
        });
        this.selected.addListener((obs, oleState, newState) -> {
            // 变化北京颜色  设置其他menu 还原 
            log.info("选择状态发生变化, 组件id --> {}", this.componentID);
            if(this.controller == null) {
                return;
            }
            // 因为传递的是接口 所以接口需要实现通用的组件传递机制 
        });
        
    }
    
    /**
     * 组件控制中心 
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
        // this.controller.bindingNodes(this);  // 这种调用会造成死循环 调用震荡 
        this.controller.bindACK(this.componentID, this);
    }

    @Override 
    public void unBinding() {
        this.controller = null;
        this.componentID = null;
    }
    
    
}









