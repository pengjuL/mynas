package com.pengju.imageuploadserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pengju.imageuploadserver.bean.NasFile;
import com.pengju.imageuploadserver.bean.User;
import com.pengju.imageuploadserver.mapper.NasFileMapper;
import com.pengju.imageuploadserver.mapper.UserMapper;
import com.pengju.imageuploadserver.service.NasFileService;
import com.pengju.imageuploadserver.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author pengju
 */
@Service
public class NasFileServiceImpl extends ServiceImpl<NasFileMapper, NasFile> implements NasFileService {

}
