package com.dr1.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dr1.system.entity.SysUser;
import com.dr1.system.mapper.SysUserMapper;
import com.dr1.system.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}

