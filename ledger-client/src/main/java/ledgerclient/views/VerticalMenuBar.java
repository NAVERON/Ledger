package ledgerclient.views;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import ledgerclient.utils.IControlBinding;
import ledgerclient.utils.IEventBinding;

public class VerticalMenuBar extends VBox implements IEventBinding {
    
    // 保存菜单控制  以后做成 自动更新 
    private ObservableList<VerticalMenuItem> menuItems = FXCollections.observableArrayList();
    
    public VerticalMenuBar() {
        this.initComponent();
    }
    public VerticalMenuBar(VerticalMenuItem... items) {
        Arrays.asList(items).stream().forEach(item -> this.menuItems.add(item));
        
        this.initComponent();
    }
    
    private void initComponent() {
        this.getChildren().addAll(menuItems);
        // Bindings.bindContentBidirectional(this.getChildren(), this.menuItems);  // observable 两个元素需要相同 
    }
    
    /**
     * 增加菜单 
     * @param item
     * @return status 状态标识, 使用map或者forEach 批量操作  
     */
    public Boolean addMenuItem(VerticalMenuItem item) {
        Boolean status = Boolean.FALSE;
        this.menuItems.add(item);
        this.getChildren().add(item);
        
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
        
        // 子组件绑定 
        this.menuItems.stream().forEach(item -> {
            item.binding(controller);
        });
    }

    @Override 
    public void unBinding() {
        this.controller = null;
        this.componentID = null;
    }
    
    
}








