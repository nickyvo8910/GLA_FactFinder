Vue.component("record-card", {
    props: ['record'],
    template:
        '<div class="card record-card d-block" :id="recordCardId">' +
        '   <div class="card-body">' +
        '       <div class="row">' +
        '           <div class="col col-sm-11">' +
        '               <h4 class="card-title">' +
        '                   <span class="badge badge-info">' +
        '                       {{ record.recordSourceName }}' +
        '                   </span> Title: <a :href="record.url" target="_blank" rel="noopener noreferrer">{{ formattedTitle }}</a>' +
        '               </h4>' +
        '           </div>' +
        '           <div class="col col-sm-1 d-flex justify-content-end">' +
        '               <button class="btn btn-outline-danger pt-0 pb-0 remove-button" v-on:click="remove()"><i class="fas fa-times"></i></button>' +
        '           </div>' +
        '       </div>' +
        '       <p class="card-text">{{ record.text }}</p>' +
        '   </div>' +
        '   <div class="card-footer container"> ' +
        '       <div class="row"> ' +
        '           <div class="col col-sm-9">' +
        '               <span class="align-middle">Created at: {{ formatTimestamp }} ({{ relativeTime }})</span>' +
        '           </div>' +
        '           <div class="col col-sm-3"> ' +
        '               <button class="btn btn-block btn-outline-info" data-toggle="modal" :data-target="getModalTargetId">More info</button> ' +
        '           </div> ' +
        '       </div> ' +
        '   </div>' +
        '   <div class="modal fade" :id="getModalId" tabindex="-1" role="dialog" aria-labelledby="moreInfoLabel" aria-hidden="true">' +
        '       <div class="modal-dialog modal-lg" role="document">' +
        '           <div class="modal-content">' +
        '               <div class="modal-header">' +
        '                   <h5 class="modal-title" id="exampleModalLabel">More info</h5>' +
        '                   <button type="button" class="close" data-dismiss="modal" aria-label="Close"></button>' +
        '               </div>' +
        '               <div class="modal-body">' +
        '                   <ul><li v-for="(value, key) in record"><p class="value-text"><span class="badge badge-info">{{ key }}</span>: {{ value }}</p></li></ul>' +
        '               </div>' +
        '               <div class="modal-footer">' +
        '                   <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>' +
        '               </div>' +
        '           </div>' +
        '       </div>' +
        '    </div>'+
        '</div>',
    computed: {
        recordCardId: function() {
            return "record--" + this.record.dbId;
        },
        getModalTargetId: function() {
            return "#record-modal-" + this.record.dbId;
        },
        getModalId: function() {
            return "record-modal-" + this.record.dbId;
        },
        formatTimestamp: function() {
            return moment.unix(this.record.createdTimestamp / 1000).format('Do MMMM YYYY, h:mm:ss a');
        },
        relativeTime: function() {
            return moment.unix(this.record.createdTimestamp / 1000).fromNow();
        },
        formattedTitle: function() {
            if (this.record.title !== "") {
                return this.record.title;
            } else {
                return this.noTitleString;
            }
        }
    },
    data: function() {
        return {
            title: "",
            textString: "",
            recordSourceName: "",
            noTitleString: "No title"
        }
    },
    methods: {
        remove: function() {
            if (confirm("Are you sure you want to delete this record ?")) {
                let xhr = new XMLHttpRequest();
                xhr.open('DELETE', jsRoutes.controllers.HomeController.remove(this.record.dbId, this.record.recordSourceName).url);
                let vm = this;
                xhr.onload = function () {
                    if (xhr.status === 200) {
                        // emit update record result
                        vm.$emit('update-result');
                        Vue.toasted.show("Record is removed!", {
                            theme: "bubble",
                            position: "bottom-center",
                            duration: 2000
                        });
                    }
                    else {
                        console.log('Request failed.  Returned status of ');
                        console.log(xhr);
                    }
                };
                xhr.send();
            } else {
                // do nothing
            }
        },
        printJson: function(record) {
            return JSON.stringify(record, null, 2);
        },
        printSelf: function() {
            this.set('createdTimestamp', this.get('createdTimestamp') + "x");
            console.log(this);
        }
    },
});