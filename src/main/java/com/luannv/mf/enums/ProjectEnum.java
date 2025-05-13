package com.luannv.mf.enums;

public enum ProjectEnum {
	PENDING, // 0. project upload few seconds
	IN_PROGRESS, // 1. project gain proposal from dev
	ASSIGNED, // 2. client and dev is ok for proposal
	COMPLETED, // 3. project is completed
	CANCELED // 4. client or dev or system canceled
}
