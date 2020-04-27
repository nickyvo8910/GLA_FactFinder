Vue.component("qe-logs-modal", {
    props: {
        qe: Object,
        id: String
    },
    data: function() {
        return {
            expLogs: []
        }
    },
    template:
        '<b-modal :title="modalTitle" :id="id" :ok-only="true" size="lg">' +
        '   <b-table hover :items="formattedTable" responsive></b-table>' +
        '</b-modal>',
    created: function() {
        this.getExpLogs();
    },
    computed: {
        formattedTable: function() {
            let vm = this;
            return this.expLogs.map(log => {
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
            return "Expansion Logs: " + this.qe.name
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
        getExpLogs: function() {
            let xhr = new XMLHttpRequest();
            xhr.open('GET', jsRoutes.controllers.ManageQEController.getExpLogs(this.qe.name).url);
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    let response = JSON.parse(xhr.response);
                    vm.expLogs = response.data;
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