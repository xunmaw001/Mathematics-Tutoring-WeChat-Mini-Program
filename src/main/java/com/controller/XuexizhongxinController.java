package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.XuexizhongxinEntity;
import com.entity.view.XuexizhongxinView;

import com.service.XuexizhongxinService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;
import com.service.StoreupService;
import com.entity.StoreupEntity;

/**
 * 学习中心
 * 后端接口
 * @author 
 * @email 
 * @date 2022-05-05 16:27:46
 */
@RestController
@RequestMapping("/xuexizhongxin")
public class XuexizhongxinController {
    @Autowired
    private XuexizhongxinService xuexizhongxinService;


    @Autowired
    private StoreupService storeupService;

    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,XuexizhongxinEntity xuexizhongxin, 
		HttpServletRequest request){

        EntityWrapper<XuexizhongxinEntity> ew = new EntityWrapper<XuexizhongxinEntity>();
		PageUtils page = xuexizhongxinService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xuexizhongxin), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,XuexizhongxinEntity xuexizhongxin, 
		HttpServletRequest request){
        EntityWrapper<XuexizhongxinEntity> ew = new EntityWrapper<XuexizhongxinEntity>();
		PageUtils page = xuexizhongxinService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xuexizhongxin), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( XuexizhongxinEntity xuexizhongxin){
       	EntityWrapper<XuexizhongxinEntity> ew = new EntityWrapper<XuexizhongxinEntity>();
      	ew.allEq(MPUtil.allEQMapPre( xuexizhongxin, "xuexizhongxin")); 
        return R.ok().put("data", xuexizhongxinService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(XuexizhongxinEntity xuexizhongxin){
        EntityWrapper< XuexizhongxinEntity> ew = new EntityWrapper< XuexizhongxinEntity>();
 		ew.allEq(MPUtil.allEQMapPre( xuexizhongxin, "xuexizhongxin")); 
		XuexizhongxinView xuexizhongxinView =  xuexizhongxinService.selectView(ew);
		return R.ok("查询学习中心成功").put("data", xuexizhongxinView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        XuexizhongxinEntity xuexizhongxin = xuexizhongxinService.selectById(id);
		xuexizhongxin.setClicknum(xuexizhongxin.getClicknum()+1);
		xuexizhongxin.setClicktime(new Date());
		xuexizhongxinService.updateById(xuexizhongxin);
        return R.ok().put("data", xuexizhongxin);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        XuexizhongxinEntity xuexizhongxin = xuexizhongxinService.selectById(id);
		xuexizhongxin.setClicknum(xuexizhongxin.getClicknum()+1);
		xuexizhongxin.setClicktime(new Date());
		xuexizhongxinService.updateById(xuexizhongxin);
        return R.ok().put("data", xuexizhongxin);
    }
    


    /**
     * 赞或踩
     */
    @RequestMapping("/thumbsup/{id}")
    public R thumbsup(@PathVariable("id") String id,String type){
        XuexizhongxinEntity xuexizhongxin = xuexizhongxinService.selectById(id);
        if(type.equals("1")) {
        	xuexizhongxin.setThumbsupnum(xuexizhongxin.getThumbsupnum()+1);
        } else {
        	xuexizhongxin.setCrazilynum(xuexizhongxin.getCrazilynum()+1);
        }
        xuexizhongxinService.updateById(xuexizhongxin);
        return R.ok();
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody XuexizhongxinEntity xuexizhongxin, HttpServletRequest request){
    	xuexizhongxin.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(xuexizhongxin);

        xuexizhongxinService.insert(xuexizhongxin);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody XuexizhongxinEntity xuexizhongxin, HttpServletRequest request){
    	xuexizhongxin.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(xuexizhongxin);

        xuexizhongxinService.insert(xuexizhongxin);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody XuexizhongxinEntity xuexizhongxin, HttpServletRequest request){
        //ValidatorUtils.validateEntity(xuexizhongxin);
        xuexizhongxinService.updateById(xuexizhongxin);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        xuexizhongxinService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<XuexizhongxinEntity> wrapper = new EntityWrapper<XuexizhongxinEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}


		int count = xuexizhongxinService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,XuexizhongxinEntity xuexizhongxin, HttpServletRequest request,String pre){
        EntityWrapper<XuexizhongxinEntity> ew = new EntityWrapper<XuexizhongxinEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicknum");
        
        params.put("order", "desc");
		PageUtils page = xuexizhongxinService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, xuexizhongxin), params), params));
        return R.ok().put("data", page);
    }







}
