package ledgerclient.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ledgerclient.utils.LogicController;

/**
 * 左侧垂直菜单单元 
 * @author eron 
 * 
 */
public class VerticalMenuItem extends HBox {
    
    private static final Logger log = LoggerFactory.getLogger(VerticalMenuItem.class);
    private VerticalMenuBar menuBar;
    
    private ImageView icon = new ImageView(
                new Image(getClass().getResource("/assets/images/1.png").toExternalForm(), true)
            );
    private Text menuItemName = new Text("DEFAULT");
    private BooleanProperty selected = new SimpleBooleanProperty(); // 当前是否选中状态 绑定组件整体的样式 
    
    public VerticalMenuItem(VerticalMenuBar menuBar) {
        this.linkMenuBar(menuBar);
        this.initComponent();
    }
    public VerticalMenuItem(String name) {
        this.menuItemName.setText(name);
        this.initComponent();
    }
    
    private void initComponent() {
        this.setAlignment(Pos.CENTER_LEFT);
        
        this.icon.setFitWidth(70);
        this.icon.setFitHeight(70);
        
        this.getChildren().addAll(icon, menuItemName);
        this.setPadding(new Insets(10));
        this.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));
        // 点击事件 改变 BooleanProperty 状态进而控制其他逻辑同步 
        this.setOnMouseClicked((event) -> {
            if(event.getClickCount() >= 2 || event.getButton() != MouseButton.PRIMARY) {
                return;
            }
            if(this.selected.getValue()) {
                Tab selectedTab = this.menuBar.createInformationTab(this.menuItemName.getText());
                return;
            }
            
            this.menuBar.unSelectAll();  // 其他的可以通过menubar中的controller实现多层传递控制 
            this.selected.setValue(!this.selected.getValue());
            
            // 控制center的显示信息面板 
            Tab selectedTab = this.menuBar.createInformationTab(this.menuItemName.getText());
            
        });
        this.selected.addListener((obs, oleState, newState) -> {
            // 变化北京颜色  设置其他menu 还原 
            log.info("选择状态发生变化, 组件id --> {}", "dede");
            // 因为传递的是接口 所以接口需要实现通用的组件传递机制 
            this.backgroundProperty().set(
                newState // ? selected  bindings.bind(select).when().otherwise 
                ? new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY))
                : new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY))
                
            );
            
        });
        
        this.setBorder(new Border(
                new BorderStroke(
                    Color.BLACK, 
                    BorderStrokeStyle.SOLID, 
                    CornerRadii.EMPTY, 
                    BorderWidths.DEFAULT
                )
            )
        );
        
    }
    
    public void linkMenuBar(VerticalMenuBar menuBar) {
        this.menuBar = menuBar;
    }
    public void unSelect() {
        this.selected.setValue(false);
    }
    
}









