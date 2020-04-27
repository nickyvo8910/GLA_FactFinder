Vue.component("crawler-card", {
    props: ['source'],
    template:
    '<div class="card record-card w-50 d-block">' +
    '   <div class="card-body"> ' +
    '       <h3 class="card-title">' +
    '           <span class="badge badge-primary">' +
    '              Source name:' +
    '           </span> {{ source.sourceName }}' +
    '       </h3> ' +
    '   </div> ' +
    '   <ul class="list-group list-group-flush">\n' +
    '       <li class="list-group-item">' +
    '           <p class="card-text source-property" v-for="(value, key) in source">' +
    '               <span class="badge badge-info">{{ key }}: </span> <value-text :value="value"></value-text>' +
    '           </p>' +
    '       </li>\n' +
    '   </ul>' +
    '   <div class="card-footer container">' +
    '       <div class="row">' +
    '           <div class="col col-sm-4">' +
    '               <button class="btn btn-block btn-outline-info" v-b-modal="crawlerResultModalId" v-on:click="forceCrawl()">Crawl</button>' +
    '           </div>' +
    '           <div class="col col-sm-4">' +
    '               <button class="btn btn-block btn-outline-warning" data-toggle="modal" :data-target="editModalTarget">Edit</button>' +
    '           </div>' +

    '           <div class="col col-sm-4">' +
    '               <button class="btn btn-block btn-outline-danger" v-on:click="deleteCrawler()">Delete</button>' +
    '           </div>' +
    '       </div>' +
    '   </div>' +
    '   <edit-form :id="editModalId" :source="source" v-on:update-crawlers="updateCrawlers()"></edit-form>' +
    '   <crawler-result-modal ref="crawlerResultModal" :id="crawlerResultModalId" :source="source" :crawler-stats="crawlerStats" v-on:update-crawlers="updateCrawlers()"></crawler-result-modal>' +
    '</div>',
    computed: {
        editModalTarget: function() {
            return "#" + this.editModalId;
        },
        editModalId: function() {
            return "source-" + this.source.sourceName +"-edit-modal";
        },
        crawlerResultModalTarget: function() {
            return "#" + this.crawlResultModalId;
        },
        crawlerResultModalId: function() {
            return "source-" + this.source.sourceName + "-crawl-result-modal"
        }
    },
    data: function() {
        return {
            crawlerStats: []
        }
    },
    methods: {
        updateCrawlers: function() {
            this.$emit("update-crawlers")
        },
        deleteCrawler: function() {
            if (confirm("Are you sure you want to delete crawler: " + this.source.sourceName +"?")) {
                let xhr = new XMLHttpRequest();
                xhr.open('DELETE', jsRoutes.controllers.ManageCrawlerController.deleteSource().url);
                xhr.setRequestHeader("Content-Type", "text/json");
                let vm = this;
                xhr.onload = function() {
                    if (xhr.status === 200) {
                        // show the updated toasted
                        Vue.toasted.show(vm.source.sourceName + " is deleted!", {
                            theme: "bubble",
                            position: "bottom-center",
                            duration : 2000
                        });
                        // refresh the crawlers
                        vm.$emit('update-crawlers')
                    }
                    else {
                        console.log('Request failed.  Returned status of ');
                        console.log(xhr);
                    }
                };
                xhr.send(JSON.stringify(this.source));

            } else {
                // do nothing
            }
        },
        forceCrawl: function() {
            this.$refs.crawlerResultModal.forceCrawl()
        }
    }
});

Vue.use(Toasted);

var app1 = new Vue({
    el: "#app1",
    created: function() {
        this.getSources();
    },
    data: {
        sources: [],
        addFormId: "add-form",
        addFormTargetId: "#add-form",
        query: {
            text: "",
            enabledSources: {}
        }
    },
    computed: {
        addCrawlerId: function() {
            return this.sources.length + 1;
        }
    },
    methods: {
        updateCrawlers: function() {
            // update search nav
            this.$refs.searchNavForm.getSources();
            // update crawler cards
            this.getSources();
        },
        updateQuery: function(query) {
          this.query = query;
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
                    vm.sources = sources;
                    // transform timestamp to string
                    for (let i = 0; i < vm.sources.length; i++) {
                        let lastCrawl = vm.sources[i].lastCrawl;
                        let nextCrawl = vm.sources[i].nextCrawl;
                        vm.sources[i].lastCrawl = vm.formatTime(lastCrawl) + vm.relativeTime(lastCrawl);
                        vm.sources[i].nextCrawl = vm.formatTime(nextCrawl) + vm.relativeTime(nextCrawl);
                    }
                }
                else {
                    console.log('Request failed.  Returned status of ');
                    console.log(xhr);
                }
            };
            xhr.send();
        },
        formatTime: function(ts) {
            if (ts !== -2)
                return moment.unix(ts).format('Do MMMM YYYY, h:mm:ss a');
            else
                return "N/A (System just started)";
        },
        relativeTime: function(ts) {
            if (ts !== -2)
                return " (" +  moment.unix(ts).fromNow() + ")";
            else
                return "";
        },
        navigateToIndex: function() {
            window.location.href = jsRoutes.controllers.HomeController.index().url
        },
        navigateToTrackQuery: function() {
            window.location.href = jsRoutes.controllers.TrackQueryController.trackQuery().url
        },
        navigateToManageQE: function() {
            window.location.href = jsRoutes.controllers.ManageQEController.manageQE().url
        }
    }
});

