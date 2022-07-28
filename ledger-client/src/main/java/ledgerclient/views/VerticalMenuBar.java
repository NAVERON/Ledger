package ledgerclient.views;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ledgerclient.utils.LogicController;


public class VerticalMenuBar extends VBox {
    
    private static final Logger log = LoggerFactory.getLogger(VerticalMenuBar.class);
    private LogicController controller;
    
    // 保存菜单控制  以后做成 自动更新 
    private ObservableList<VerticalMenuItem> menuItems = FXCollections.observableArrayList();
    
    public VerticalMenuBar() {
        this.initComponent();
    }
    public VerticalMenuBar(LogicController controller) {
        this.controller = controller;
        this.initComponent();
    }
    public VerticalMenuBar(VerticalMenuItem... items) {
        Arrays.asList(items).stream().forEach(item -> {
            item.linkMenuBar(this);
            this.menuItems.add(item);
        });
        
        this.initComponent();
    }
    
    private void initComponent() {
        // this.getChildren().addAll(menuItems);
        Bindings.bindContent(this.getChildren(), this.menuItems);  // 绑定动态变化的组件 
        
        this.setBorder(new Border(
                new BorderStroke(
                    Color.BLACK, 
                    BorderStrokeStyle.SOLID, 
                    CornerRadii.EMPTY, 
                    BorderWidths.DEFAULT
                )
            )
        );
        
        this.styleProperty().bind(
            Bindings
            .when(hoverProperty())
            .then(new SimpleStringProperty("-fx-background-color: #43CD80;"))
            .otherwise(new SimpleStringProperty("-fx-background-color: #F4F4F4;"))
        );
        
    }
    
    /**
     * 增加菜单 
     * @param item
     * @return status 状态标识, 使用map或者forEach 批量操作  
     */
    public Boolean addMenuItem(VerticalMenuItem item) {
        Boolean status = Boolean.FALSE;
        item.linkMenuBar(this);
        this.menuItems.add(item);
        // this.getChildren().add(item);
        
        return status;
    }
    public void addMenuItems(List<VerticalMenuItem> items) {
        items.stream().forEach(item -> this.addMenuItem(item));
        // 可以直接使用menuitem直接添加 这样做方便后期修改, 如果在添加的时候额外动作, 直接改动一处即可 
    }
    public void addMenuItems(VerticalMenuItem... items) {
        // 迭代 方便后期维护, 直接修改 addMenuItem(VerticalMenuItem item) 即可全部同步修改 
        Arrays.asList(items).stream().forEach(item -> this.addMenuItem(item));
    }
    
    public void bindController(LogicController controller) {
        this.controller = controller;
        this.controller.linkMenuBar(this);
    }
    
    public void unSelectAll() {
        this.menuItems.stream().forEach(item -> item.unSelect());
    }
    
}








