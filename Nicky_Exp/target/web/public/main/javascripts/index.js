let app1 = new Vue({
    el: "#app1",
    created: function() {
        // get all sources
        this.getSources()
    },
    data: {
        isQueryExisted: false,
        existedQuery: {},
        query: "",
        sources: [],
        loading: false,
    },
    methods: {
        checkQuery: function() {
            this.loading = true;
            // call the ajax js routing
            let xhr = new XMLHttpRequest();
            xhr.open('GET', jsRoutes.controllers.HomeController.checkQuery(this.query).url);
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    // query is existed
                    let response = JSON.parse(xhr.response);
                    let status = response.status;
                    if (status === "OK") {
                        let query = response.data;
                        vm.isQueryExisted = true;
                        vm.existedQuery = query;
                        // apply isEnabledSources to the setting
                        for (let i = 0; i < vm.sources.length; i++) {
                            vm.sources[i].isEnabledCrawling = vm.existedQuery.enabledSources[vm.sources[i].sourceName]
                        }
                    } else {
                        // no query existed
                        vm.isQueryExisted = false;
                        vm.existedQuery = {};
                        // revert the setting back to default: all true
                        for (let i = 0; i < vm.sources.length; i++) {
                            vm.sources[i].isEnabledCrawling = true;
                        }
                    }
                }
                else {
                    // error on request
                    vm.isQueryExisted = false;
                    vm.existedQuery = {};
                }
                vm.loading = false;
                vm.$refs.queryExistModalRef.show()
            };
            xhr.send();
        },
        submitQuery: function() {
            let vm = this;
            let enabledSources = {};
            for (let i = 0; i < this.sources.length; i++) {
                console.log(this.sources[i]);
                if (this.sources[i].hasOwnProperty("sourceName") && this.sources[i].hasOwnProperty("isEnabledCrawling")) {
                    console.log("has prop");
                    enabledSources[this.sources[i].sourceName] = this.sources[i].isEnabledCrawling;
                }
                else {
                    // missing property

                }
            }
            // compose data
            let data = JSON.stringify({
                "enabledSources": enabledSources
            });
            // add query & setting to the submit query url
            let url = jsRoutes.controllers.HomeController.submitQuery(this.query, data).url;
            console.log(url);
            // redirect
            window.location.href = url
        },
        getSources: function() {
            // call the ajax js routing
            let xhr = new XMLHttpRequest();
            xhr.open('GET', jsRoutes.controllers.ManageCrawlerController.getSources().url);
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    let response = JSON.parse(xhr.response);
                    let sources = response.data;
                    vm.sources = sources.map(source => {
                        source['isEnabledCrawling'] = true;
                        return source;
                    });
                }
                else {
                    console.log('Request failed.  Returned status of ');
                    console.log(xhr);
                }
            };
            xhr.send();
        }
    }
});