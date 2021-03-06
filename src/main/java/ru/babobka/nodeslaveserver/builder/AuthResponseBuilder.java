package ru.babobka.nodeslaveserver.builder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ru.babobka.container.Container;
import ru.babobka.nodeserials.Mappings;
import ru.babobka.nodeserials.NodeResponse;
import ru.babobka.nodeserials.RSA;
import ru.babobka.nodeslaveserver.task.TaskPool;

/**
 * Created by dolgopolov.a on 29.10.15.
 */
public class AuthResponseBuilder {

	private AuthResponseBuilder() {

	}

	private static final TaskPool taskPool = Container.getInstance().get(TaskPool.class);

	public static NodeResponse build(RSA rsa, String user, String password) {

		Map<String, Serializable> addition = new HashMap<>();
		addition.put("login", user);
		addition.put("password", rsa.encrypt(password));
		List<String> tasksList = new LinkedList<>();
		tasksList.addAll(taskPool.getTasksMap().keySet());
		addition.put("tasksList", (Serializable) tasksList);
		return new NodeResponse(UUID.randomUUID(), UUID.randomUUID(), 0, NodeResponse.Status.NORMAL, null, addition,
				Mappings.AUTH_TASK_NAME);

	}

}
