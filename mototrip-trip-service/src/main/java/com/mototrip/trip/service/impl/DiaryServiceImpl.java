package com.mototrip.trip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mototrip.common.exception.ConflictException;
import com.mototrip.common.exception.ForbiddenException;
import com.mototrip.common.exception.NotFoundException;
import com.mototrip.common.response.PageResult;
import com.mototrip.trip.dto.request.CreateDiaryRequest;
import com.mototrip.trip.dto.request.UpdateDiaryRequest;
import com.mototrip.trip.entity.Diary;
import com.mototrip.trip.entity.UserLike;
import com.mototrip.trip.mapper.DiaryMapper;
import com.mototrip.trip.mapper.UserLikeMapper;
import com.mototrip.trip.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryMapper diaryMapper;
    private final UserLikeMapper userLikeMapper;

    @Override
    public PageResult<Diary> findAll(Long userId, Long tripId, int page, int pageSize) {
        LambdaQueryWrapper<Diary> wrapper = new LambdaQueryWrapper<>();
        if (userId != null) wrapper.eq(Diary::getUserId, userId);
        if (tripId != null) wrapper.eq(Diary::getTripId, tripId);
        wrapper.orderByDesc(Diary::getCreatedAt);
        Page<Diary> pageResult = diaryMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }

    @Override
    public Diary findById(Long id) {
        Diary diary = diaryMapper.selectById(id);
        if (diary == null) throw new NotFoundException("日记不存在");
        return diary;
    }

    @Override
    public Diary create(Long userId, CreateDiaryRequest request) {
        Diary diary = new Diary();
        diary.setTitle(request.getTitle());
        diary.setContent(request.getContent());
        diary.setImages(request.getImages());
        diary.setLocation(request.getLocation());
        diary.setLocationName(request.getLocationName());
        diary.setWeather(request.getWeather());
        diary.setTemperature(request.getTemperature());
        diary.setMood(request.getMood() != null ? request.getMood() : "neutral");
        diary.setLikes(0);
        diary.setComments(0);
        diary.setTag(request.getTag());
        diary.setUserId(userId);
        diary.setTripId(request.getTripId());
        diaryMapper.insert(diary);
        return diary;
    }

    @Override
    public Diary update(Long userId, Long id, UpdateDiaryRequest request) {
        Diary diary = diaryMapper.selectById(id);
        if (diary == null) throw new NotFoundException("日记不存在");
        if (!diary.getUserId().equals(userId)) throw new ForbiddenException("无权修改此日记");
        if (request.getTitle() != null) diary.setTitle(request.getTitle());
        if (request.getContent() != null) diary.setContent(request.getContent());
        if (request.getImages() != null) diary.setImages(request.getImages());
        if (request.getLocation() != null) diary.setLocation(request.getLocation());
        if (request.getLocationName() != null) diary.setLocationName(request.getLocationName());
        if (request.getWeather() != null) diary.setWeather(request.getWeather());
        if (request.getTemperature() != null) diary.setTemperature(request.getTemperature());
        if (request.getMood() != null) diary.setMood(request.getMood());
        if (request.getTag() != null) diary.setTag(request.getTag());
        if (request.getTripId() != null) diary.setTripId(request.getTripId());
        diaryMapper.updateById(diary);
        return diary;
    }

    @Override
    public void delete(Long userId, Long id) {
        Diary diary = diaryMapper.selectById(id);
        if (diary == null) throw new NotFoundException("日记不存在");
        if (!diary.getUserId().equals(userId)) throw new ForbiddenException("无权删除此日记");
        diaryMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void like(Long userId, Long id) {
        Diary diary = diaryMapper.selectById(id);
        if (diary == null) throw new NotFoundException("日记不存在");
        Long count = userLikeMapper.selectCount(
                new LambdaQueryWrapper<UserLike>()
                        .eq(UserLike::getUserId, userId)
                        .eq(UserLike::getTargetType, "diary")
                        .eq(UserLike::getTargetId, id)
        );
        if (count > 0) throw new ConflictException("已经点赞过了");
        UserLike like = new UserLike();
        like.setUserId(userId);
        like.setTargetType("diary");
        like.setTargetId(id);
        userLikeMapper.insert(like);
        diary.setLikes(diary.getLikes() + 1);
        diaryMapper.updateById(diary);
    }
}
