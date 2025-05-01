package com.luannv.mf.mappers;

public interface GenericMapper<Entity, Request, Response> {
	Entity toEntity(Request request);

	Response toResponse(Entity entity);

}
