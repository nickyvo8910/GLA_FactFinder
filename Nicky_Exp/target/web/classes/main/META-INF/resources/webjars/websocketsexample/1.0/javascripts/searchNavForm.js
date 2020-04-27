Vue.component("crawler-switch", {
    props: ['source', 'is-result-page'],
    template:
        '<div class="col-sm-8">' +
        '    <div v-if="isResultPage" class="row">' +
        '        <div class="col-sm-6 p-0">{{ source.key }}</div>' +
        '        <div class="col-sm-6 p-0">' +
        '            <label class="switch">' +
        '                <input type="checkbox" v-model="source.value" v-on:change="updateSource()">' +
        '                <span class="slider round"></span>' +
        '            </label>' +
        '        </div>' +
        '    </div>' +
        '    <div v-else class="row">' +
        '        <div class="col-sm-6 p-0">{{ source.sourceName }}</div>' +
        '        <div class="col-sm-6 p-0">' +
        '            <label class="switch">' +
        '                <input type="checkbox" v-model="source.isEnabledCrawling" >' +
        '                <span class="slider round"></span>' +
        '            </label>' +
        '        </div>' +
        '    </div>' +
        '</div>',
    methods: {
        updateSource: function() {
            this.$emit('update-source', this.source)
        }
    }
});

Vue.component("search-nav-form", {
    props: {
        query: Object,
        isResultPage: Boolean
    },
    created: function() {
        // Not result page: fetch all sources
        if (!this.isResultPage) {
            this.getSources()
        }
    },
    data: function() {
        return {
            isQueryExisted: false,
            enabledSources: [],
            sources: [],
            testSources: []
        };
    },
    computed: {
        enabledSourcesArray: function() {
            if (this.query.enabledSources !== undefined)
                return Object.entries(this.query.enabledSources).map(([key, value]) => ({key,value}));
            else
                return [];
        }
    },
    template:
        '<form class="inline d-flex flex-column">' +
        '    <div class="input-group d-flex flex-row">' +
        '        <input type="text" class="form-control full-width" id="query-input" name="query" v-model="query.text">' +
        '        <div class="input-group-append">' +
        '            <button type="button" class="btn btn-outline-secondary" data-toggle="collapse" data-target="#query-setting-collapse" aria-controls="query-setting-collapse" aria-expanded="false" aria-label="Toggle navigation"><i class="fas fa-cog"></i></button>' +
        '            <button type="button" class="btn btn-outline-info" v-on:click="checkQuery()">Check</button>' +
        '            <button type="button" class="btn btn-outline-success" v-on:click="submitQuery()">Search</button>' +
        '        </div>' +
        '    </div>' +
        '    <div class="collapse" id="query-setting-collapse">' +
        '        <div class="bg-dark p-4">' +
        '            <div class="card mx-auto" id="query-setting-options">' +
        '                <div class="card-body container-fluid">' +
        '                    <h5 class="card-title">Query on: </h5>' +
        '                    <div class="mx-0 px-0" v-if="isResultPage"><crawler-switch v-for="source in enabledSourcesArray" :source="source" :key="source.key" :is-result-page="isResultPage" v-on:update-source="updateSource"></crawler-switch></div>' +
        '                    <div class="mx-0 px-0" v-else><crawler-switch v-for="source in sources" :source="source" :key="source.sourceName" :is-result-page="isResultPage"></crawler-switch></div>' +
        '                </div>' +
        '            </div>' +
        '        </div>' +
        '    </div>' +
        '    <b-modal ref="queryExistModalRef" id="query-exist-modal" :ok-only="true">' +
        '        <div slot="modal-title">' +
        '            Check Query:' +
        '            <span v-if="isQueryExisted" class="badge badge-success">Found</span>' +
        '            <span v-else class="badge badge-warning">Not Found</span>' +
        '        </div>' +
        '        <div v-if="isQueryExisted" >' +
        '            <div class="alert alert-success">' +
        '                <b>"{{ query.text }}"</b> is <i><u>existed</u></i> in the system database!' +
        '            </div>' +
        '            <div class="alert alert-info" role="alert">' +
        '                Apply <b>enabledSources</b> to the <b>query on</b> setting' +
        '            </div>' +
        '            <p class="break-word">This is the information of the query:</p>' +
        '            <p class="break-word mb-1" v-for="(value, key) in query"><span class="badge badge-secondary">{{ key }}</span> {{ value }}</p>' +
        '        </div>' +
        '        <div v-else>' +
        '            <div class="alert alert-warning">' +
        '                <b>"{{ query.text }}"</b> is <i><u>not existed</u></i> in the system database' +
        '            </div>' +
        '        </div>' +
        '    </b-modal>' +
        '</form>',
    methods: {
        updateSource: function(source) {
            console.log("changed source: ", source);
            let query = this.query;
            query.enabledSources[source.key] = source.value;
            this.$emit('update-query', query)
        },
        checkQuery: function() {
            // call the ajax js routing
            let xhr = new XMLHttpRequest();
            xhr.open('GET', jsRoutes.controllers.HomeController.checkQuery(this.query.text).url);
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    // query is existed
                    let response = JSON.parse(xhr.response);
                    let status = response.status;
                    if (status === "OK") {
                        if (vm.isResultPage) {
                            let query = response.data;
                            vm.isQueryExisted = true;
                            vm.$emit('update-query', query);
                        } else {
                            let query = response.data;
                            vm.isQueryExisted = true;
                            vm.$emit('update-query', query);
                            // apply isEnabledSources to the setting
                            for (let i = 0; i < vm.sources.length; i++) {
                                vm.sources[i].isEnabledCrawling = query.enabledSources[vm.sources[i].sourceName]
                            }
                        }
                    } else {
                        // no query existed
                        if (vm.isResultPage) {
                            vm.isQueryExisted = false;
                        } else {
                            // no query existed
                            vm.isQueryExisted = false;
                            // vm.$emit('update-query', {
                            //     text: vm.query.text
                            // });
                            // revert the setting back to default: all true
                            for (let i = 0; i < vm.sources.length; i++) {
                                vm.sources[i].isEnabledCrawling = true;
                            }
                        }
                    }
                }
                else {
                    // error on request
                    if (vm.isResultPage) {
                        vm.isQueryExisted = false;
                    } else {
                        // no query existed
                        vm.isQueryExisted = false;
                        // vm.$emit('update-query', {});
                        // revert the setting back to default: all true
                        for (let i = 0; i < vm.sources.length; i++) {
                            vm.sources[i].isEnabledCrawling = true;
                        }
                    }
                }
                vm.$refs.queryExistModalRef.show()
            };
            xhr.send();
        },
        submitQuery: function() {
            console.log("submit query: ", this.query.enabledSources);
            let vm = this;
            let enabledSources = {};
            // compose data
            let data = "";
            if (this.isResultPage) {
                data = JSON.stringify({
                    "enabledSources": this.query.enabledSources
                });
            } else {
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
                data = JSON.stringify({
                    "enabledSources": enabledSources
                });
            }
            console.log("data", data);
            // add query & setting to the submit query url
            let url = jsRoutes.controllers.HomeController.submitQuery(this.query.text, data).url;
            console.log(url);
            // redirect
            window.location.href = url
        },
        setEnabledSources: function(enabledSources) {
            this.enabledSources = enabledSources;
        },
        setIsResultPage: function(b) {
            this.isResultPage = b;
        },
        setSources: function(sources) {
            console.log(sources);
            this.testSources = sources;
            this.sources = sources.map(source => {
                source['isEnabledCrawling'] = true;
                return source;
            });
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
                    vm.setSources(sources);
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