package com.ruoyi.framework.datasource;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 *  动态数据源
 *  Spring boot提供了AbstractRoutingDataSource 根据用户定义的规则选择当前的数据源，
 *  这样我们可以在执行查询之前，设置使用的数据源。实现可动态路由的数据源，在每次数据库查询操作前执行。
 *  它的抽象方法 determineCurrentLookupKey() 决定使用哪个数据源。
 *  AbstractRoutingDataSource的多数据源动态切换的核心逻辑是：在程序运行时，把数据源数据源通过 AbstractRoutingDataSource
 *  动态织入到程序中，灵活的进行数据源切换。
 * 基于AbstractRoutingDataSource的多数据源动态切换，可以实现读写分离，这么做缺点也很明显，无法动态的增加数据源。
 *
 * 实现逻辑：
 *
 * 定义DynamicDataSource类继承抽象类AbstractRoutingDataSource，并实现了determineCurrentLookupKey()方法。
 * 把配置的多个数据源会放在AbstractRoutingDataSource的 targetDataSources和defaultTargetDataSource中，
 * 然后通过afterPropertiesSet()方法将数据源分别进行复制到resolvedDataSources和resolvedDefaultDataSource中。
 * 调用AbstractRoutingDataSource的getConnection()的方法的时候，先调用determineTargetDataSource()方法返回DataSource在进行getConnection()。
 *  AbstractRoutingDataSource 只支持单库事务，也就是说切换数据源要在开启事务之前执行。 spring DataSourceTransactionManager进行事务管理，
 *  开启事务，会将数据源缓存到DataSourceTransactionObject对象中进行后续的commit rollback等事务操作。
 *
 *  出现多数据源动态切换失败的原因是因为在事务开启后，数据源就不能再进行随意切换了，也就是说，一个事务对应一个数据源。
 *
 * 传统的Spring管理事务是放在Service业务层操作的，所以更换数据源的操作要放在这个操作之前进行。也就是切换数据源操作放在Controller层，
 * 可是这样操作会造成Controller层代码混乱的结果。
 *
 * 故而想到的解决方案是将事务管理在数据持久 (Dao层) 开启，切换数据源的操作放在业务层进行操作，就可在事务开启之前顺利进行数据源切换，不会再出现切换失败了。
 * @author ruoyi
 */

public class DynamicDataSource extends AbstractRoutingDataSource
{
    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources)
    {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey()
    {
        //DynamicDataSourceContextHolder类，保存及获取数据源
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
