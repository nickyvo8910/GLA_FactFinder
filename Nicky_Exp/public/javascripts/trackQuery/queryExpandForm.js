Vue.component("query-expand-form", {
    props: ['id', 'query'],
    template:
        '   <div class="modal fade" tabindex="-1" role="dialog" :id="id">' +
        '       <div class="modal-dialog modal-lg" role="document">' +
        '           <div class="modal-content">' +
        '               <div class="modal-header">' +
        '                   <h5 class="modal-title">Expand query</h5>' +
        '                   <button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
        '                       <span aria-hidden="true">&times;</span>' +
        '                   </button>' +
        '               </div>' +
        '               <div class="modal-body">' +
        '                   <b-tabs>' +
        '                       <b-tab v-for="qe in qes" :key="qe.name" :title="qe.name">' +
        '                           <query-generator-card :qe="qe" :key="qe.name" v-on:force-generate-from-query="forceGenerateFromQuery"></query-generator-card>' +
        '                       </b-tab>' +
        '                   </b-tabs>' +
        '               </div>' +
        '               <div class="modal-footer">' +
        '                   <input class="mw-100 w-100" placeholder="Enter expanded query here and click add" v-model="expandedQuery">' +
        '                   <button type="button" class="btn btn-primary" v-on:click="addQuery()" data-dismiss="modal">Add</button>' +
        '                   <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>' +
        '               </div>' +
        '           </div>' +
        '       </div>' +
        '   </div>',
    created: function() {
        this.getQesInfo()
    },
    data: function() {
        return {
            expandedQuery: "",
            qes: [],
            topN: 8,
            loading: true
        }
    },
    computed: {

    },
    methods: {
        forceGenerateFromQuery: function(qeElement) {
            let qeName = qeElement.qe.name;
            qeElement.loading = true;
            // call force generate
            let xhr = new XMLHttpRequest();
            xhr.open('GET', jsRoutes.controllers.TrackQueryController.forceGenerateFromQuery(this.query.dbId, qeName).url);
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    let getQesInfoXhr = new XMLHttpRequest();
                    // update qes info
                    getQesInfoXhr.open('GET', jsRoutes.controllers.TrackQueryController.getQesInfo(vm.query.dbId).url);
                    getQesInfoXhr.onload = function() {
                        if (getQesInfoXhr.status === 200) {
                            let response = JSON.parse(getQesInfoXhr.response);
                            vm.qes = response.data;
                            qeElement.loading = false;
                        }
                        else {
                            console.log('Request failed.  Returned status of ');
                            console.log(getQesInfoXhr);
                        }
                    };
                    getQesInfoXhr.send();
                }
                else {
                    console.log('Request failed.  Returned status of ');
                    console.log(xhr);
                }
            };
            xhr.send();
        },
        formatTimestamp: function(ts) {
            return  moment.unix(ts).format();
        },
        getTop: function(rankedTerms) {
            return rankedTerms.slice(0, this.topN)
        },
        getQesInfo: function() {
            // request the info by giving queryId and update qgs
            let xhr = new XMLHttpRequest();
            xhr.open('GET', jsRoutes.controllers.TrackQueryController.getQesInfo(this.query.dbId).url);
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    let response = JSON.parse(xhr.response);
                    vm.qes = response.data;
                    vm.loading = false;
                }
                else {
                    console.log('Request failed.  Returned status of ');
                    console.log(xhr);
                }
            };
            xhr.send();
        },
        addQuery: function() {
            let xhr = new XMLHttpRequest();
            xhr.open('PUT', jsRoutes.controllers.TrackQueryController.addManualQueryExpansion().url);
            xhr.setRequestHeader("Content-Type", "text/json");
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    let response = JSON.parse(xhr.response);
                    Vue.toasted.show("\"" + vm.query.text + "\" is expanded! (expanded query = \"" + vm.expandedQuery + "\")", {
                        theme: "bubble",
                        position: "bottom-center",
                        duration : 2000
                    });
                    // refresh the track queries
                    vm.$emit('update-queries', response.data);
                }
                else {
                    console.log('Request failed.  Returned status of ');
                    console.log(xhr);
                }
            };
            let requestObj = {
                prevQueryId: vm.query.dbId,
                expandedQuery: vm.expandedQuery
            };
            console.log("sending", requestObj);
            xhr.send(JSON.stringify(requestObj));
        }
    }
});