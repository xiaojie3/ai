package com.example.systemadministration.controller;


import com.example.edu.common.MyUtils;
import com.example.systemadministration.dto.UserDto;
import com.example.systemadministration.service.UserService;
import com.example.systemadministration.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<Long> create(@RequestBody UserVo vo) {
        UserDto dto = userService.create(MyUtils.copyProperties(vo, UserDto.class)).orElseThrow();
        return ResponseEntity.ok(dto.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/account/{account}")
    public ResponseEntity<UserVo> findByAccount(@PathVariable String account) {
        UserDto dto = userService.findByAccount(account).orElseThrow();
        return ResponseEntity.ok(MyUtils.copyProperties(dto, UserVo.class));
    }
}
