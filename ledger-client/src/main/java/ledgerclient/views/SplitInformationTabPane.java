package ledgerclient.views;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ledgerclient.utils.LogicController;

public class SplitInformationTabPane extends TabPane {

    private static final Logger log = LoggerFactory.getLogger(SplitInformationTabPane.class);
    private LogicController controller;
    
    private Map<String, Tab> tabMap = new HashMap<>();
    
    public SplitInformationTabPane() {
        super();
    }
    public SplitInformationTabPane(LogicController controller) {
        super();
        
        this.bindController(controller);
    }
    public SplitInformationTabPane(Tab... tabs) {
        super(tabs);
    }
    
    private void initComponent() {
        
        this.setTabClosingPolicy(TabClosingPolicy.SELECTED_TAB);
        this.setOnMouseClicked(e -> {
            log.info("鼠标点击");
        });
        
        this.setPadding(new Insets(20));
    }

    public void bindController(LogicController controller) {
        this.controller = controller;
        this.controller.linkInformationTabPane(this);
    }
    
    public Tab createTab(String tabName) {  // 每一个tab设置一个名字,通过名字控制 
        if(this.tabMap.containsKey(tabName)) {
            log.info("已经存在, 无需创建,直接获取");
            this.getSelectionModel().select(this.tabMap.get(tabName));
            
            return this.tabMap.get(tabName);
        }
        
        Tab tab = new Tab(tabName);
        this.tabMap.put(tabName, tab);
        
        Parent content = this.createTabContent(tabName);
        tab.setContent(content);
        tab.setText(tabName);
        tab.setGraphic(new Circle(20, Color.RED));
        tab.setOnCloseRequest(e ->{
            this.tabMap.remove(tabName);
        });
        this.getSelectionModel().select(tab);
        
        this.getTabs().add(tab);
        
        return tab;
    }
    
    private Parent createTabContent(String tabName) {
        VBox vb = new VBox();
        vb.getChildren().add(new Label("当前tab" + tabName));
        TextArea ta = new TextArea();
        vb.getChildren().add(ta);
        
        Button info = new Button("获取流水");
        vb.getChildren().add(info);
        info.setOnAction(e -> {
            if(tabName.equals("用户管理")) {
                // 请求用户数据 
                ta.setText("获取的结果");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                
                String result = this.controller.getUserinformation();
                ta.setText(result);
            }else if(tabName.equals("流水管理")) {
                
                ta.setText("流水结果");
                
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
                String result = this.controller.getRecentBusiness();
                ta.setText(result);
            }
        });
        
        return vb;
    }
    
    
}







