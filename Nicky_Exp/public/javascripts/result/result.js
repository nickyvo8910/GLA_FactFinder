Vue.use(Toasted);

let app1 = new Vue({
    el: "#app1",
    created: function() {
        this.getResult();
    },
    computed: {
    },
    data: {
        data: {},
        numberOfShownRecords: 0,
        filterOrderByRecords: [],
        navItem: "nav-item",
        query: {},
        settingData: {},
        sources: [],
        // loading spinner
        loading: true,
        spinnerColor: "5dc596",
        spinnerSize: 300,
        // filter by
        filterBy: {
            source: "all",
            title: "all",
            text: "all"
        },
        // order by
        orderBy: {
            createdTime: "nto"
        },
        // pagination
        pagination: {
            recordsPerPage: 10,
            page: 1,
            totalPages: 1,
            iFirstRecord: 1,
            iLastRecord: 1
        },
        isInvalidInput: false
    },
    methods: {
        updateQuery: function(query) {
            console.log("in result.js: updateQuery", query.enabledSources);
            this.query = query;
        },
        findGetParameters: function(parameterName) {
            let result = null,
                tmp = [];
            location.search
                .substr(1)
                .split("&")
                .forEach(function (item) {
                    tmp = item.split("=");
                    if (tmp[0] === parameterName) result = decodeURIComponent(tmp[1]);
                });
            return result;
        },
        clickRecordsPerPage: function() {
            this.pagination.page = 1;
            this.updateResult();
        },
        clickFilterBySource: function(source) {
            this.filterBy.source = source;
            this.updateResult();
        },
        getResult: function() {
            let urlString = window.location.href;
            let url = new URL(urlString);
            let q = url.searchParams.get("query");
            let settingData = JSON.parse(url.searchParams.get("data"));
            settingData.filterBy = this.filterBy;
            settingData.orderBy = this.orderBy;
            settingData.pagination = this.pagination;
            this.settingData = settingData;
            // call the ajax js routing
            let xhr = new XMLHttpRequest();
            xhr.open('GET', jsRoutes.controllers.HomeController.getResult(q, JSON.stringify(settingData)).url);
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    let response = JSON.parse(xhr.response);
                    vm.data = response.data;
                    vm.sources = response.sources;
                    vm.query = response.query;
                    // pagination
                    vm.pagination = response.data.pagination;
                    // filer order by records
                    vm.filterOrderByRecords = response.data.filterOrderByRecords;
                    // numberOfShownRecords
                    vm.numberOfShownRecords = response.data.numberOfShownRecords;
                    vm.loading = false
                }
                else {
                    if (xhr.status === 400) {
                        // invalid input
                        vm.loading = false;
                        // show the message
                        vm.isInvalidInput = true;
                        let response = JSON.parse(xhr.response);
                        let emptyQuery= response.emptyQuery;
                        vm.query = emptyQuery;
                        // vm.$refs.searchNav.setSources(sources);
                        // vm.$refs.searchNav.setIs
                    } else {
                        console.log('Request failed.  Returned status of ');
                        console.log(xhr);
                    }
                }
            };
            xhr.send();
        },
        updateResult: function() {
            let urlString = window.location.href;
            let url = new URL(urlString);
            let q = url.searchParams.get("query");
            let settingData = JSON.parse(url.searchParams.get("data"));
            settingData.filterBy = this.filterBy;
            settingData.orderBy = this.orderBy;
            settingData.pagination = this.pagination;
            this.settingData = settingData;
            // call the ajax js routing
            let xhr = new XMLHttpRequest();
            xhr.open('GET', jsRoutes.controllers.HomeController.updateResult(q, JSON.stringify(settingData)).url);
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    let response = JSON.parse(xhr.response);
                    vm.data = response.data;
                    vm.sources = response.sources;
                    // pagination
                    vm.pagination = response.data.pagination;
                    // filer order by records
                    vm.filterOrderByRecords = response.data.filterOrderByRecords;
                    // numberOfShownRecords
                    vm.numberOfShownRecords = response.data.numberOfShownRecords;
                }
                else {
                    console.log('Request failed.  Returned status of ');
                    console.log(xhr);
                }
            };
            xhr.send();
        },
        navigateToTrackQuery: function() {
            window.location.href = jsRoutes.controllers.TrackQueryController.trackQuery().url
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

