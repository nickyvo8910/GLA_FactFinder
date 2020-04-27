Vue.use(Toasted);

let app1 = new Vue({
    el: "#app1",
    created: function() {
        this.getQueries()
    },
    data: {
        query: {},
        queries: []
    },
    computed: {

    },
    methods: {
        updateQuery: function(query) {
            this.query = query;
        },
        updateQueries: function() {
            this.getQueries();
        },
        getQueries: function() {
            // call the ajax js routing
            let xhr = new XMLHttpRequest();
            xhr.open('GET', jsRoutes.controllers.TrackQueryController.getQueries().url);
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    let response = JSON.parse(xhr.response);
                    vm.queries = response.data;
                }
                else {
                    console.log('Request failed.  Returned status of ');
                    console.log(xhr);
                }
            };
            xhr.send();
        },
        navigateToIndex: function() {
            window.location.href = jsRoutes.controllers.HomeController.index().url
        },
        navigateToManageCrawler: function() {
            window.location.href = jsRoutes.controllers.ManageCrawlerController.manageCrawler().url
        },
        navigateToManageQE: function() {
            window.location.href = jsRoutes.controllers.ManageQEController.manageQE().url
        }
    }
});

