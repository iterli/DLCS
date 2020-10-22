package com.ruoyi.project.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.domain.TreeSelect;
import com.ruoyi.project.system.domain.SysArea;
import com.ruoyi.project.system.mapper.SysAreaMapper;
import com.ruoyi.project.system.service.ISysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 区域Service业务层处理
 *
 * @author ruoyi
 * @date 2020-10-09
 */
@Service
public class SysAreaServiceImpl implements ISysAreaService
{
    @Autowired
    private SysAreaMapper sysAreaMapper;

    /**
     * 查询区域
     *
     * @param areaId 区域ID
     * @return 区域
     */
    @Override
    public SysArea selectSysAreaById(Long areaId)
    {
        return sysAreaMapper.selectSysAreaById(areaId);
    }

    /**
     * 查询区域列表
     *
     * @param sysArea 区域
     * @return 区域
     */
    @Override
    public List<SysArea> selectSysAreaList(SysArea sysArea)
    {
        return sysAreaMapper.selectSysAreaList(sysArea);
    }

    /**
     * 新增区域
     *
     * @param sysArea 区域
     * @return 结果
     */
    @Override
    public int insertSysArea(SysArea sysArea)
    {
        sysArea.setCreateTime(DateUtils.getNowDate());
        return sysAreaMapper.insertSysArea(sysArea);
    }

    /**
     * 修改区域
     *
     * @param sysArea 区域
     * @return 结果
     */
    @Override
    public int updateSysArea(SysArea sysArea)
    {
        sysArea.setUpdateTime(DateUtils.getNowDate());
        return sysAreaMapper.updateSysArea(sysArea);
    }

    /**
     * 批量删除区域
     *
     * @param areaIds 需要删除的区域ID
     * @return 结果
     */
    @Override
    public int deleteSysAreaByIds(Long[] areaIds)
    {
        return sysAreaMapper.deleteSysAreaByIds(areaIds);
    }

    /**
     * 删除区域信息
     *
     * @param areaId 区域ID
     * @return 结果
     */
    @Override
    public int deleteSysAreaById(Long areaId)
    {
        return sysAreaMapper.deleteSysAreaById(areaId);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param areas 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildAreaTreeSelect(List<SysArea> areas) {
        List<SysArea> areaTrees = buildAreaTree(areas);
//          有一个集合：
//          List<User> users = getList(); //从数据库查询的用户集合
//          现在想获取User的身份证号码；在后续的逻辑处理中要用；
//          常用的方法我们大家都知道，用for循环，
//          List<String> idcards=new ArrayList<String>();//定义一个集合来装身份证号码
//          for(int i=0;i<users.size();i++){
//　　       idcards.add(users.get(i).getIdcard());
//        }
//        这种方法要写好几行代码，有没有简单点的，有，java8 API能一行搞定：
//        List<String> idcards= users.stream().map(User::getIdcard).collect(Collectors.toList())
//        解释下一这行代码：
//        users：一个实体类的集合，类型为List<User>
//        User：实体类
//        getIdcard：实体类中的get方法，为获取User的idcard

        //TreeSelect::new 代表访问的是TreeSelect的构造方法
          return areaTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param areas 区域列表
     * @return 树结构列表
     */
    @Override
    public List<SysArea> buildAreaTree(List<SysArea> areas) {
        List<SysArea> returnList = new ArrayList<SysArea>();
        List<Long> temList = new ArrayList<Long>();
        for(SysArea area : areas){
            temList.add(area.getAreaId());
        }
        for(Iterator iterator = areas.iterator(); iterator.hasNext();){
            SysArea area = (SysArea)iterator.next();
            // contains用来判断字符串是否包含子字符串
            // 如果temList集合中不包含area.getParentId()的话，证明该节点是顶级节点,则遍历该父节点的所有子节点
            if(!temList.contains(area.getParentId())){
                recursionFn(areas, area);
                returnList.add(area);
            }
        }
        if(returnList.isEmpty()){
            returnList = areas;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    public void recursionFn(List<SysArea> list,SysArea a){
        List<SysArea> areaList = getChildList(list,a);
        a.setChildren(areaList);
        for(SysArea are : areaList){
            if(hasChild(list,are)){
                Iterator iter = areaList.iterator();
                while(iter.hasNext()){
                    SysArea area = (SysArea)iter.next();
                    recursionFn(list,area);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysArea> getChildList(List<SysArea> list, SysArea a)
    {
        List<SysArea> alist = new ArrayList<SysArea>();
        Iterator<SysArea> it = list.iterator();
        while (it.hasNext())
        {
            SysArea p = (SysArea) it.next();
            if (StringUtils.isNotNull(p.getParentId()) && p.getParentId().longValue() == a.getAreaId().longValue())
            {
                alist.add(p);
            }
        }
        return alist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysArea> list, SysArea a)
    {
        return getChildList(list, a).size() > 0 ? true : false;
    }

    @Override
    public int updateAreaStatus(SysArea area) {
        return sysAreaMapper.updateSysArea(area);
    }
}
