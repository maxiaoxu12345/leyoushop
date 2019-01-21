package com.leyou.web;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.pojo.Group;
import com.leyou.pojo.Param;
import com.leyou.pojo.User;
import com.leyou.service.SpecService;
import com.leyou.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @date 2019/1/7-21:10
 */
@RestController
@RequestMapping("spec")
public class SpecController {
	@Autowired
	SpecService specService;
	@GetMapping("groups/{id}")
	public ResponseEntity<List<Group>> getGroup(@PathVariable("id")  Long id){
		return ResponseEntity.status(HttpStatus.CREATED).body(specService.getGroup(id));
	}
	@GetMapping("params")
	public ResponseEntity<List<Param>> getParam(@RequestParam(value = "gid",required = false)  Long gid,
												@RequestParam(value = "cid",required = false)  Long cid,
												@RequestParam(value = "searching",required = false)  Boolean searching){
		return ResponseEntity.status(HttpStatus.CREATED).body(specService.getParam(gid,cid,searching));
	}
	@GetMapping("groups")
	public ResponseEntity<List<Group>> getGroups(@RequestParam("cid") Long cid){
		return ResponseEntity.ok(specService.getGroups(cid));
	};
}
