Vue.component("query-card", {
    props: ['query'],
    template:
    '<div class="card record-card w-50 d-block">' +
    '   <div class="card-body"> ' +
    '       <h3 class="card-title">' +
    '           <span class="badge badge-primary">' +
    '              Text:' +
    '           </span> {{ query.text }}' +
    '       </h3> ' +
    '   </div> ' +
    '   <ul class="list-group list-group-flush">' +
    '       <li class="list-group-item">' +
    '           <h4><span class="badge badge-info">Number of records:</span></h4>' +
    '           <p v-for="relatedRecords in query.numberOfRelatedRecords" class="mb-1">{{ relatedRecords[0] }}: {{ relatedRecords[1] }}</p>' +
    '           <p class="mb-1"><b>Total records: {{ totalRelatedRecords }}</b></p>' +
    '       </li>' +
    '       <li class="list-group-item">' +
    '           <h4><span class="badge badge-info">Created at:</span></h4> {{ formatTimestamp }} ({{ relativeTime }})' +
    '       </li>\n' +
    '   </ul>' +
    '   <ul class="list-group list-group-flush">\n' +
    '       <li class="list-group-item">' +
    '           <h4><span class="badge badge-info">Crawler</span></h4>' +
    '           <div class="container">' +
    '               <div class="row" v-for="(value, key) in query.enabledSources">' +
    '                   <div class="col-sm-4">{{ key }}</div>' +
    '                   <div class="col-sm-8">' +
    '                       <label class="switch">' +
    '                           <input type="checkbox" v-on:change="change(key)" v-model="query.enabledSources[key]">' +
    '                           <span class="slider round"></span>' +
    '                       </label>' +
    '                   </div>' +
    '               </div>' +
    '           </div>' +
    '       </li>\n' +
    '   </ul>' +
    '   <div class="card-footer container"> ' +
    '       <div class="row"> ' +
    '           <div class="col col-sm-4">' +
    '               <button class="btn btn-block btn-outline-warning" data-toggle="modal" :data-target="expandModalTarget">Expand</button>' +
    '           </div> ' +
    '           <div class="col col-sm-4">' +
    '               <button class="btn btn-block btn-outline-info" v-on:click="navigateToVisualisation()">Visualisation</button>' +
    '           </div> ' +
    '           <div class="col col-sm-4"> ' +
    '               <button class="btn btn-block btn-outline-danger" v-on:click="deleteQuery()">Delete</button> ' +
    '           </div> ' +
    '       </div> ' +
    '   </div>' +
    '   <query-expand-form :id="expandModalId" :query="query" v-on:update-queries="updateQueries"></query-expand-form>' +
    '</div>',
    computed: {
        formatTimestamp: function() {
            return  moment.unix(this.query.timestamp).format('Do MMMM YYYY, h:mm:ss a');
        },
        relativeTime: function() {
            return moment.unix(this.query.timestamp).fromNow();
        },
        expandModalId: function() {
            return "expand-modal-" + this.query.dbId
        },
        expandModalTarget: function() {
            return "#" + this.expandModalId
        },
        totalRelatedRecords: function() {
            return this.query.numberOfRelatedRecords.map(r => {
                return r[1]
            }).reduce((accumulator, currentValue) => accumulator + currentValue)
        }
    },
    data: function() {
        return {
            expandModal: "expand-modal"
        }
    },
    methods: {
        navigateToVisualisation: function() {
            window.location.href = jsRoutes.controllers.VisualisationController.getVisualisation(this.query.dbId).url;
        },
        updateQueries: function() {
            this.$emit("update-queries");
        },
        change: function(sourceName) {
            let isCrawling = this.query.enabledSources[sourceName];
            let newStatus = isCrawling ? "enabled" : "disabled";
            let xhr = new XMLHttpRequest();
            xhr.open('PUT', jsRoutes.controllers.TrackQueryController.changeEnabledSources().url);
            xhr.setRequestHeader("Content-Type", "text/json");
            xhr.onload = function() {
                if (xhr.status === 200) {
                    // show the updated toasted
                    Vue.toasted.show(sourceName + " is now " + newStatus, {
                        theme: "bubble",
                        position: "bottom-center",
                        duration : 2000
                    });
                }
                else {
                    Vue.toasted.show("There is an error occurred", {
                        theme: "bubble",
                        position: "bottom-center",
                        duration : 2000
                    });
                    console.log('Request failed.  Returned status of ');
                    console.log(xhr);
                }
            };
            xhr.send(JSON.stringify({
                enabledSources: this.query.enabledSources,
                dbId: this.query.dbId
            }));
        },
        deleteQuery: function() {
            if (confirm("Are you sure you want to delete this query: " + "?")) {
                let xhr = new XMLHttpRequest();
                xhr.open('DELETE', jsRoutes.controllers.TrackQueryController.deleteQuery().url);
                xhr.setRequestHeader("Content-Type", "text/json");
                let vm = this;
                xhr.onload = function() {
                    if (xhr.status === 200) {
                        // show the updated toasted
                        Vue.toasted.show(vm.query.text + " is deleted!", {
                            theme: "bubble",
                            position: "bottom-center",
                            duration : 2000
                        });
                        // refresh the queries
                        vm.updateQueries();
                    }
                    else {
                        console.log('Request failed.  Returned status of ');
                        console.log(xhr);
                    }
                };
                xhr.send(JSON.stringify(this.query));
            } else {
                // do nothing
            }
        }
    },
});