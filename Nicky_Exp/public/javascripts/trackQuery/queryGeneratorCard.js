Vue.component("query-generator-card", {
    props: ['qe'],
    template:
        '<div class="card record-card w-100 d-block">' +
        '   <div class="row">' +
        '       <div class="card-body col-sm-9">' +
        '           <h4 class="card-title pl-3">' +
        '               <span class="badge badge-primary">' +
        '               Query Generator Class:' +
        '               </span> {{ qe.qgName }}' +
        '           </h4>' +
        '       </div>' +
        '       <div class="card-body col-sm-3">' +
        '           <button class="btn btn-warning" v-on:click="forceGenerate()">Generate now</button>' +
        '       </div>' +
        '   </div>' +
        '   <ul class="list-group list-group-flush">' +
        '       <spinner :loading="loading" class="mb-4"></spinner>' +
        '       <li v-show="!loading" class="list-group-item pt-3">' +
        '           <h4><span class="badge badge-info">Query Generator Logs: Ranked Terms</span></h4>' +
        '           <div class="container">' +
        '               <div class="row info-record" v-for="infoRecord in qe.info">' +
        '                   <div class="col-sm-2">CreatedAt: {{ formatTimestamp(infoRecord.createdTimestamp) }} ({{ relativeTime(infoRecord.createdTimestamp) }})</div>' +
        '                   <div class="col-sm-10">' +
        '                       <div class="row">' +
        '                           <b-card v-for="term in getTop(infoRecord.rankedTerms)" :key="term[0]" class="col-sm-4 p-0" no-body>' +
        '                               <b-card-header class="p-1 pl-3"><h4>{{ term[0] }}</h4></b-card-header>' +
        '                               <b-card-body>' +
        '                                   <p v-for="(value, key) in term[1]" class="m-0">{{key}}: {{formatDecimal(value)}}</p>' +
        '                               </b-card-body>' +
        '                           </b-card>' +
        '                       </div>' +
        '                   </div>' +
        '               </div>' +
        '           </div>' +
        '       </li>' +
        '   </ul>' +
        '</div>',
    computed: {

    },
    data: function() {
        return {
            topN: 8,
            loading: false,
        }
    },
    methods: {
        formatDecimal: function(n) {
            if (Number.isInteger(n))
                return n;
            else
                return n.toFixed(3);
        },
        getTop: function(rankedTerms) {
            return rankedTerms.slice(0, this.topN)
        },
        formatTimestamp: function(ts) {
            return  moment.unix(ts).format('Do MMMM YYYY, h:mm:ss a');
        },
        relativeTime: function(ts) {
            return moment.unix(ts).fromNow();
        },
        forceGenerate: function() {
            // this.loading = true;
            this.$emit('force-generate-from-query', this)
        }
    },
});