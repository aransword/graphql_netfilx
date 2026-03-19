package com.netflix.content_service.graphql;

import com.netflix.content_service.model.Show;
import com.netflix.content_service.repository.ShowRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.DgsEntityFetcher;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@DgsComponent
@RequiredArgsConstructor
public class ContentDataFetcher {

    private final ShowRepository showRepository;

    // 1. 단일 컨텐츠 조회 (Query의 show 필드와 매핑)
    @DgsQuery(field = "show")
    public Show show(@InputArgument Long id) {
        return showRepository.findById(id).orElse(null);
    }

    // 2. 전체 컨텐츠 조회 (Query의 show 필드와 매핑)
    @DgsQuery(field = "shows")
    public List<Show> shows() {
        return showRepository.findAll();
    }

    // ⭐ Federation: 다른 서비스에서 Show를 ID로 참조할 때 호출됨
    @DgsEntityFetcher(name = "Show")
    public Show resolveShowReference(Map<String, Object> values) {
        String idStr = (String) values.get("id");
        if (idStr != null) {
            Long id = Long.parseLong(idStr);
            return showRepository.findById(id).orElse(null);
        }
        return null;
    }
}