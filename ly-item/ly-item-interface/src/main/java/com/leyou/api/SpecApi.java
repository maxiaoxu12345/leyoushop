package com.leyou.api;

import com.leyou.pojo.Group;
import com.leyou.pojo.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @date 2019/1/11-15:30
 */
public interface SpecApi {
	@GetMapping("spec/params")
	public List<Param> getParam(@RequestParam(value = "gid",required = false)  Long gid,
												@RequestParam(value = "cid",required = false)  Long cid,
												@RequestParam(value = "searching",required = false)  Boolean searching);
	@GetMapping("spec/groups")
	public List<Group> getGroups(@RequestParam("cid") Long cid);
}
