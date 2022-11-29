package com.project.board.global.datasource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReplicationRoutingConstants {

    MASTER("master"),
    SLAVE("slave");

    public final String type;
}
