package ledgerclient.utils;

/**
 * 提供给组件使用 实现组件和控制中心的双向绑定 
 * @author wangy
 *
 */
public interface IEventBinding {

    // 提供绑定接口 任何一个实现了绑定接口的组件 都可以实现和controller的绑定
    public String getComponentID();
    
    public void binding(IControlBinding controller);  // 组件 被动绑定 
    public void unBinding();
    
}
