package ledgerclient.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import ledgerclient.utils.LogicController;

public class SplitInformationTabPane extends TabPane {

    private static final Logger log = LoggerFactory.getLogger(SplitInformationTabPane.class);
    private LogicController controller;
    public SplitInformationTabPane() {
        super();
    }
    public SplitInformationTabPane(LogicController controller) {
        super();
    }
    public SplitInformationTabPane(Tab... tabs) {
        super(tabs);
    }
    
    private void initComponent() {
        
        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        this.setOnMouseClicked(e -> {
            log.info("鼠标点击");
        });
        
        this.setPadding(new Insets(20));
    }

    public void bindController(LogicController controller) {
        this.controller = controller;
        this.controller.linkInformationTabPane(this);
    }
    
}
