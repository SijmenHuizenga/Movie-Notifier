package it.sijmen.jump.listeners;

import it.sijmen.jump.JumpRequest;

public interface CreateListener<T> extends ActorListener<T> {

    /**
     * Before anything gets done, the opportunity to check the request details.
     */
    void checkCreateRequest(JumpRequest request);

    /**
     * Just after the model is parsed, the opportunity to check weather or not
     * the requester is allowed to create the model.
     * @return true when authorized.
     */
    boolean allowCreate(JumpRequest request, T model);

    /**
     * Before the model is validated the opportunity to apply some
     * transformations or custom pre-validations.
     * @return the model to continue the process with. If nothing is
     *   changed on the model, return the given model.
     */
    T beforeCreateValidation(T model);

    /**
     * Before the model is stored in the repository the opportunity
     * to apply some transformations or custom pre-validations.
     * @return the model to continue the process with. If nothing is
     *   changed on the model, return the given model.
     */
    T beforeCreateStore(T model);

}
