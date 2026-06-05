package com.dr1.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dr1.common.core.ApiResult;
import com.dr1.common.core.PageResult;
import com.dr1.common.exception.BusinessException;
import com.dr1.system.dto.UserRequest;
import com.dr1.system.entity.SysUser;
import com.dr1.system.service.SysUserService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final SysUserService sysUserService;

    public UserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @GetMapping
    public ApiResult<List<SysUser>> list() {
        return ApiResult.ok(sysUserService.list());
    }

    @GetMapping("/page")
    public ApiResult<PageResult<SysUser>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        Page<SysUser> page = sysUserService.page(new Page<>(current, size));
        return ApiResult.ok(new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize()));
    }

    @GetMapping("/{id}")
    public ApiResult<SysUser> detail(@PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        return ApiResult.ok(user);
    }

    @PostMapping
    public ApiResult<SysUser> create(@Valid @RequestBody UserRequest request) {
        SysUser user = new SysUser();
        fillUser(user, request);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        sysUserService.save(user);
        return ApiResult.ok(user);
    }

    @PutMapping("/{id}")
    public ApiResult<SysUser> update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        SysUser user = sysUserService.getById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        fillUser(user, request);
        user.setUpdateTime(LocalDateTime.now());
        sysUserService.updateById(user);
        return ApiResult.ok(user);
    }

    @DeleteMapping("/{id}")
    public ApiResult<Void> delete(@PathVariable Long id) {
        sysUserService.removeById(id);
        return ApiResult.ok();
    }

    @GetMapping("/health")
    public ApiResult<String> health() {
        return ApiResult.ok("dr1-system is running");
    }

    private void fillUser(SysUser user, UserRequest request) {
        user.setUsername(request.getUsername());
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setStatus(request.getStatus() == null ? 1 : request.getStatus());
    }
}

