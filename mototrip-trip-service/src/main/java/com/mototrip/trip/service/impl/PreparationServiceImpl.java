package com.mototrip.trip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mototrip.common.exception.ForbiddenException;
import com.mototrip.common.exception.NotFoundException;
import com.mototrip.common.response.PageResult;
import com.mototrip.trip.dto.request.CreatePreparationRequest;
import com.mototrip.trip.dto.request.UpdatePreparationRequest;
import com.mototrip.trip.entity.Preparation;
import com.mototrip.trip.mapper.PreparationMapper;
import com.mototrip.trip.service.PreparationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreparationServiceImpl implements PreparationService {

    private final PreparationMapper preparationMapper;

    @Override
    public PageResult<Preparation> findAll(Long userId, String category, int page, int pageSize) {
        LambdaQueryWrapper<Preparation> wrapper = new LambdaQueryWrapper<>();
        if (userId != null) wrapper.eq(Preparation::getUserId, userId);
        if (category != null && !category.isEmpty()) wrapper.eq(Preparation::getCategory, category);
        wrapper.orderByAsc(Preparation::getCategory);
        Page<Preparation> pageResult = preparationMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }

    @Override
    public Preparation create(Long userId, CreatePreparationRequest request) {
        Preparation prep = new Preparation();
        prep.setName(request.getName());
        prep.setCategory(request.getCategory() != null ? request.getCategory() : "other");
        prep.setDescription(request.getDescription());
        prep.setIsEssential(request.getIsEssential() != null ? request.getIsEssential() : false);
        prep.setIsPacked(request.getIsPacked() != null ? request.getIsPacked() : false);
        prep.setQuantity(request.getQuantity() != null ? request.getQuantity() : 1);
        prep.setUserId(userId);
        preparationMapper.insert(prep);
        return prep;
    }

    @Override
    public Preparation update(Long userId, Long id, UpdatePreparationRequest request) {
        Preparation prep = preparationMapper.selectById(id);
        if (prep == null) throw new NotFoundException("准备物品不存在");
        if (!prep.getUserId().equals(userId)) throw new ForbiddenException("无权修改此物品");
        if (request.getName() != null) prep.setName(request.getName());
        if (request.getCategory() != null) prep.setCategory(request.getCategory());
        if (request.getDescription() != null) prep.setDescription(request.getDescription());
        if (request.getIsEssential() != null) prep.setIsEssential(request.getIsEssential());
        if (request.getIsPacked() != null) prep.setIsPacked(request.getIsPacked());
        if (request.getQuantity() != null) prep.setQuantity(request.getQuantity());
        preparationMapper.updateById(prep);
        return prep;
    }

    @Override
    public void delete(Long userId, Long id) {
        Preparation prep = preparationMapper.selectById(id);
        if (prep == null) throw new NotFoundException("准备物品不存在");
        if (!prep.getUserId().equals(userId)) throw new ForbiddenException("无权删除此物品");
        preparationMapper.deleteById(id);
    }

    @Override
    public void toggle(Long userId, Long id) {
        Preparation prep = preparationMapper.selectById(id);
        if (prep == null) throw new NotFoundException("准备物品不存在");
        if (!prep.getUserId().equals(userId)) throw new ForbiddenException("无权操作此物品");
        prep.setIsPacked(!prep.getIsPacked());
        preparationMapper.updateById(prep);
    }
}
