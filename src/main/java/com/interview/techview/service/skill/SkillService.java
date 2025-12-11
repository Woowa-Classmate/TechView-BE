package com.interview.techview.service.skill;

import com.interview.techview.domain.skill.Skill;
import com.interview.techview.dto.skill.SkillCreateRequest;
import com.interview.techview.dto.skill.SkillResponse;
import com.interview.techview.dto.skill.SkillUpdateRequest;
import com.interview.techview.exception.CustomException;
import com.interview.techview.exception.ErrorCode;
import com.interview.techview.repository.skill.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    // 기술스택 성성
    public SkillResponse createSkill(SkillCreateRequest request) {

        if (skillRepository.existsByName(request.getName())) {
            throw new CustomException(ErrorCode.DUPLICATE_SKILL);
        }

        Skill skill = skillRepository.save(
                Skill.builder().name(request.getName()).build()
        );

        return SkillResponse.from(skill);
    }

    // 기술스택 조회
    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(SkillResponse::from)
                .toList();
    }
    
    // 기술스택 수정
    public SkillResponse updateSkill(Long id, SkillUpdateRequest request) {

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SKILL_NOT_FOUND));

        if (request.getName() != null) {
            if (skillRepository.existsByName(request.getName())) {
                throw new CustomException(ErrorCode.DUPLICATE_SKILL);
            }
            skill.updateName(request.getName());
        }

        skillRepository.save(skill);

        return SkillResponse.from(skill);
    }

    // 기술스택 삭제
    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new CustomException(ErrorCode.SKILL_NOT_FOUND);
        }
        skillRepository.deleteById(id);
    }
}
