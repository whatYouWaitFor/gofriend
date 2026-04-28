package com.greg.gofriend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greg.gofriend.model.Tag;
import com.greg.gofriend.service.TagService;
import com.greg.gofriend.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author dgj
* @description 针对表【tag(标签表)】的数据库操作Service实现
* @createDate 2026-04-17 14:25:21
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




