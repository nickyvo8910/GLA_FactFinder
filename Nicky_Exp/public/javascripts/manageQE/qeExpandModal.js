Vue.component("qe-expand-modal", {
    props: {
        qe: Object,
        id: String
    },
    data: function() {
        return {
            loading: true,
            newLogs: []
        }
    },
    template:
        '<b-modal :title="modalTitle" :id="id" ok-only size="lg">' +
        '   <spinner :loading="loading">' +
        '   </spinner>' +
        '   <div v-show="!loading">' +
        '       <b-alert show variant="success">' +
        '           Successfully expanding queries!' +
        '       </b-alert>' +
        '       <b-table hover :items="formattedTable" responsive></b-table>' +
        '   </div>' +
        '</b-modal>',
    computed: {
        formattedTable: function() {
            let vm = this;
            return this.newLogs.map(log => {
                delete log.queryDbId;
                delete log.expQueryDbId;
                delete log._id;
                let ts = log.createdTimestamp;
                let createdTimestampText = vm.formatTime(ts) + vm.relativeTime(ts);
                delete log.createdTimestamp;
                delete log.qeName;
                log.fromQuery = log.queryText;
                delete log.queryText;
                log.expandedQuery = log.expQueryText;
                delete log.expQueryText;
                log.expandedAt = createdTimestampText;
                return log;
            })
        },
        modalTitle: function() {
            return "Expand: " + this.qe.name
        }
    },
    methods: {
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
        forceExpand: function() {
            this.loading = true;
            this.newLogs = [];
            let xhr = new XMLHttpRequest();
            xhr.open('GET', jsRoutes.controllers.ManageQEController.forceExpand(this.qe.name).url);
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    let response = JSON.parse(xhr.response);
                    vm.newLogs = response.data;
                    vm.$emit('update-logs');
                    vm.loading = false;
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