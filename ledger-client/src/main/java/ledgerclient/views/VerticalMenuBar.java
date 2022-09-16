package ledgerclient.views;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ledgerclient.utils.LogicController;


public class VerticalMenuBar extends VBox {
    
    private static final Logger log = LoggerFactory.getLogger(VerticalMenuBar.class);
    private LogicController controller;
    private HBox addMenu = new HBox();  // 单独管理 
    
    // 保存菜单控制  以后做成 自动更新 
    private ObservableList<VerticalMenuItem> menuItems = FXCollections.observableArrayList();
    
    public VerticalMenuBar() {
        this.initComponent();
    }
    public VerticalMenuBar(LogicController controller) {
        this.bindController(controller);
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
        this.setSpacing(10);
        this.setBorder(new Border(
                new BorderStroke(
                    Color.BLACK, 
                    BorderStrokeStyle.SOLID, 
                    CornerRadii.EMPTY, 
                    BorderWidths.DEFAULT
                )
            )
        );
        
        this.backgroundProperty().bind(
            Bindings.when(hoverProperty())
            .then(new Background(new BackgroundFill(Color.DARKGREEN, CornerRadii.EMPTY, Insets.EMPTY)))
            .otherwise(new Background(new BackgroundFill(Color.BLUEVIOLET, CornerRadii.EMPTY, Insets.EMPTY)))
        );
        
        // 新增菜单按钮 
        this.getChildren().add(this.addMenu);
        Text add = new Text("+");
        add.setFont(Font.font(50));
        this.addMenu.getChildren().add(add);
        this.addMenu.setAlignment(Pos.CENTER);
        this.addMenu.setBackground(
                new Background(new BackgroundFill(Color.GOLDENROD, CornerRadii.EMPTY, Insets.EMPTY))
            );
        this.addMenu.setOnMouseClicked(e ->{
            if(this.menuItems.size() >= 4) {
                log.info("增加的菜单过多");
                return;
            }
            // 增加一个新的菜单 
            VerticalMenuItem item = new VerticalMenuItem("新增菜单");
            item.linkMenuBar(this);
            this.menuItems.add(item);
        });
        this.addMenu.setBorder(new Border(
            new BorderStroke(
                Color.BLACK, 
                BorderStrokeStyle.SOLID, 
                CornerRadii.EMPTY, 
                BorderWidths.DEFAULT
            )
        ));
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

    public void select(String tabKey) {
    	this.unSelectAll();
    	this.menuItems.stream().filter(item -> item.getMenuItemName().equals(tabKey)).findFirst().ifPresent(item -> item.select());;
    }
    
    public Tab createInformationTab(String tabName) {
        return this.controller.makeSPlitInformationCreateTab(tabName);
    }
}








