package inu.appcenter.demo.service;

import inu.appcenter.demo.domain.Team;
import inu.appcenter.demo.exception.TeamException;
import inu.appcenter.demo.model.team.TeamSaveRequest;
import inu.appcenter.demo.model.team.TeamUpdateRequest;
import inu.appcenter.demo.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    // 팀을 만들 때는 중복된 이름이 있으면 안된다.
    @Transactional
    public void saveTeam(TeamSaveRequest teamSaveRequest) {
        // 중복 체크
        validateDuplicatedTeamName(teamSaveRequest.getName());

        // 팀 저장
        Team team = Team.createTeam(teamSaveRequest.getName());
        teamRepository.save(team);
    }

    private void validateDuplicatedTeamName(String name) {
        Optional<Team> result = teamRepository.findByName(name);
        if (result.isPresent()) {
            throw new TeamException("이름이 중복되었습니다.");
        }
    }

    @Transactional
    public void deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException("존재하지 않는 팀 id 입니다."));
        teamRepository.delete(team);
    }

    @Transactional
    public void updateTeam(Long teamId, TeamUpdateRequest teamUpdateRequest) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamException("존재하지 않는 팀 id입니다."));
        // 중복 체크
        validateDuplicatedTeamName(teamUpdateRequest.getName());

        team.changeName(teamUpdateRequest.getName());
    }


    public List<Team> findAll() {
        List<Team> teams = teamRepository.findAll();
        return teams;
    }

    public Team findById(Long teamId) {
        Team team = teamRepository.findWithMemberById(teamId)
                .orElseThrow(() -> new TeamException("존재하지 않는 팀 id입니다."));
        return team;
    }
}
