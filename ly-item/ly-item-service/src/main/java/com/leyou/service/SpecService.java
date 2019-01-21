package com.leyou.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.mapper.ParamMapper;
import com.leyou.mapper.SpecMapper;
import com.leyou.pojo.Group;
import com.leyou.pojo.Param;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sun.plugin.javascript.navig.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date 2019/1/7-21:17
 */
@Service
public class SpecService {
	@Autowired
	SpecMapper specMapper;
	@Autowired
	ParamMapper paramMapper;
	public List<Group> getGroup(Long id) {
		Group group = new Group();
		group.setCid(id);
		List<Group> groups = specMapper.select(group);
		if (CollectionUtils.isEmpty(groups) ){
				throw new LyException(ExceptionEnum.GROUP_IS_NOT_FIND);
		}
		return groups;
	}

	public List<Param> getParam(Long gid, Long cid, Boolean searching) {
		Param param = new Param();
		param.setGroup_id(gid);
		param.setCid(cid);
		param.setSearching(searching);
		List<Param> params = paramMapper.select(param);
		if (CollectionUtils.isEmpty(params) ){
			throw new LyException(ExceptionEnum.PARAM_IS_NOT_FIND);
		}
		return params;
	}

	public List<Group> getGroups(Long cid) {
		List<Group> groups = getGroup(cid);
		List<Param> params = getParam(null, cid, null);
		Map<Long,List<Param>> map=new HashMap<>();
		for (Param param : params) {
			if (!map.containsKey(param.getGroup_id())){
				map.put(param.getGroup_id(),new ArrayList<>());
			}
			map.get(param.getGroup_id()).add(param);
		}
		for (Group group : groups) {
			group.setParams(map.get(group.getId()));
		}


		return groups;
	}
}
