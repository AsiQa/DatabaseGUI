package actions;

public class ActionManager {



    private AddAction addAction;
    private DeleteAction deleteAction;
    private UpdateAction updateAction;
    private CountAndAverageAction countAndAverageAction;
    private SearchAction searchAction;
    private FilterAction filterAction;
    private OrderByAction orderByAction;


    public ActionManager(){initialiseActions();}

    public void initialiseActions(){

        addAction = new AddAction();
        deleteAction = new DeleteAction();
        updateAction = new UpdateAction();
        countAndAverageAction = new CountAndAverageAction();
        searchAction = new SearchAction();
        filterAction = new FilterAction();
        orderByAction = new OrderByAction();

    }

    public FilterAction getFilterAction() {
        return filterAction;
    }

    public void setFilterAction(FilterAction filterAction) {
        this.filterAction = filterAction;
    }

    public OrderByAction getOrderByAction() {
        return orderByAction;
    }

    public void setOrderByAction(OrderByAction orderByAction) {
        this.orderByAction = orderByAction;
    }

    public SearchAction getSearchAction() {
        return searchAction;
    }

    public void setSearchAction(SearchAction searchAction) {
        this.searchAction = searchAction;
    }

    public AddAction getAddAction() {
        return addAction;
    }

    public void setAddAction(AddAction addAction) {
        this.addAction = addAction;
    }

    public DeleteAction getDeleteAction() {
        return deleteAction;
    }

    public void setDeleteAction(DeleteAction deleteAction) {
        this.deleteAction = deleteAction;
    }

    public UpdateAction getUpdateAction() {
        return updateAction;
    }

    public void setUpdateAction(UpdateAction updateAction) {
        this.updateAction = updateAction;
    }

    public CountAndAverageAction getCountAndAverageAction() {
        return countAndAverageAction;
    }

    public void setCountAndAverageAction(CountAndAverageAction countAndAverageAction) {
        this.countAndAverageAction = countAndAverageAction;
    }


}
