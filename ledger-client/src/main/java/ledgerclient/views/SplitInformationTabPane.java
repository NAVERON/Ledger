package ledgerclient.views;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import ledgerclient.utils.IControlBinding;
import ledgerclient.utils.IEventBinding;

public class SplitInformationTabPane extends TabPane implements IEventBinding {

    private static final Logger log = LoggerFactory.getLogger(SplitInformationTabPane.class);
    
    public SplitInformationTabPane(Tab... tabs) {
        super(tabs);
    }
    
    private void initComponent() {
        
        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        this.setOnMouseClicked(e -> {
            log.info("鼠标点击");
        });
        
    }
    
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
