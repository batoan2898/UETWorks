package com.uet.uetworks.ui.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.uet.uetworks.adapter.NewMessageAdapter
import com.uet.uetworks.api.Api
import com.uet.uetworks.api.ApiBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.LinearLayoutManager
import com.uet.uetworks.MySharedPreferences
import com.uet.uetworks.R
import com.uet.uetworks.adapter.PostAdapter
import com.uet.uetworks.model.*
import com.uet.uetworks.ui.MainActivity
import kotlin.collections.ArrayList
import com.uet.uetworks.ui.PostDetailFragment
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener


@Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "UNREACHABLE_CODE")
class HomeFragment : Fragment(), NewMessageAdapter.OnClickMessage, PostAdapter.OnClickPost {


    var totalPage: Int = 0
    private var lastPage: Boolean = true
    private var dataPost = MutableLiveData<java.util.ArrayList<Content?>>()
    private var dataPostResponse: ArrayList<Content> = arrayListOf()
    private var dataMessage = MutableLiveData<java.util.ArrayList<NewMessage?>>()
    private lateinit var newMessageAdapter: NewMessageAdapter
    private lateinit var postAdapter: PostAdapter
    lateinit var api: Api
    private var dataResponse: ArrayList<NewMessage> = arrayListOf()
    private var dataFitResponse: ArrayList<Company> = arrayListOf()
    private var dataOtherRespone: ArrayList<Company> = arrayListOf()
    var companyId: Int = 0
    var otherCompanyId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = LinearLayoutManager(requireContext())
        recyclerNewMessage.layoutManager = manager
        recyclerNewMessage.setHasFixedSize(true)
        newMessageAdapter = NewMessageAdapter(context, this)
        recyclerNewMessage.adapter = newMessageAdapter

        val managerPost = LinearLayoutManager(requireContext())
        recyclerPost.layoutManager = managerPost
        recyclerPost.setHasFixedSize(true)
        postAdapter = PostAdapter(context, this)
        recyclerPost.adapter = postAdapter
        api = ApiBuilder.client?.create(Api::class.java)!!

        getInternship()
        getDataNewMessage()
        getDataFit()
        getDataOther()
        initView()
        checkFollowFit()
        checkFollowOther("Recruitment_other")
//        postInternship()
        initFilterPartner()
        initFilterOther()
        initClickFollowPartner()
        initClickFollowOther()
        initClickInternship()

    }

    private fun initClickInternship() {
        btnInternship.setOnClickListener {
            if (btnInternship.text == getText(R.string.enrol)) {
                postInternship()
            }else{

            }
        }
    }

    private fun postInternship() {
        api.postInternship(MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : Callback<Internship> {
                override fun onFailure(call: Call<Internship>, t: Throwable) {
                    Log.e("postInternship", t.message)
                }

                override fun onResponse(call: Call<Internship>, response: Response<Internship>) {
                    if (response.code() == 200) {
                        getInternship()
                        checkFollowOther("Recruitment_other")
                        checkFollowFit()
                    }
                }
            })
    }


    private fun getDataOther() {
        api.getOther(MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : Callback<ArrayList<ArrayList<Number>>> {
                override fun onFailure(call: Call<ArrayList<ArrayList<Number>>>, t: Throwable) {
                    Log.e("getOther", t.message)

                }

                override fun onResponse(
                    call: Call<ArrayList<ArrayList<Number>>>,
                    response: Response<ArrayList<ArrayList<Number>>>
                ) {
                    for (i in 0 until response.body()?.size!!) {
                        var company = Company(
                            response.body()!![i][0].toString(),
                            response.body()!![i][1].toString()
                        )
                        dataOtherRespone.add(company)
                    }
                }

            })
    }

    private fun getDataFit() {
        api.getFit(MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : Callback<ArrayList<ArrayList<Number>>> {
                override fun onFailure(call: Call<ArrayList<ArrayList<Number>>>, t: Throwable) {
                    Log.e("getFit", t.message)
                }

                override fun onResponse(
                    call: Call<ArrayList<ArrayList<Number>>>,
                    response: Response<ArrayList<ArrayList<Number>>>
                ) {
                    for (i in 0 until response.body()?.size!!) {
                        var company = Company(
                            response.body()!![i][0].toString(),
                            response.body()!![i][1].toString()
                        )
                        dataFitResponse.add(company)
                    }

                }

            })

    }

    private fun initClickFollowOther() {
        btnFollowOther.setOnClickListener {
            followOther()
        }
    }

    private fun followOther() {
        var otherCompanyId = CompanyId(otherCompanyId)
        var otherCompanyAdditional = CompanyAdditional(otherCompanyId, "Recruitment_other")
        api.addCompany(
            otherCompanyAdditional,
            MySharedPreferences.getInstance(requireContext()).getToken()
        )
            .enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("addFollowOther", t.message)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    checkFollowOther("Recruitment_other")
                    getInternship()
                    getDataNewMessage()

                }

            })
    }

    private fun initClickFollowPartner() {
        btnAddFollowPartner.setOnClickListener {
            if (btnAddFollowPartner.text == getString(R.string.cancel)) {
                unfollowPartner(0, null)
            } else {
                addFollowPartner()

            }
        }

    }

    private fun addFollowPartner() {
        var companyId = CompanyId(companyId)
        var companyAdditional = CompanyAdditional(companyId, "Recruitment")
        api.addCompany(
            companyAdditional,
            MySharedPreferences.getInstance(requireContext()).getToken()
        )
            .enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("addFollowPartner", t.message)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    checkFollowFit()
                    getInternship()
                    getDataNewMessage()

                }
            })
    }

    private fun addOther() {
        val name = edtOther1.text.toString()
        val address = edtOther2.text.toString()
        val email = edtOther3.text.toString()
        val phone = edtOther4.text.toString()
        val partner = Partner(name, address, email, phone)
        val other = PartnerOther(null, partner, "Recruitment_other")
        if (name.isNotEmpty() && address.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
            api.addOtherByStudent(
                other,
                MySharedPreferences.getInstance(requireContext()).getToken()
            )
                .enqueue(object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.e("addOther", t.message)
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.e("addOther", response.code().toString())
                        Toast.makeText(
                            requireContext(),
                            "Ghi nhận thành công, chờ xét duyệt từ khoa",
                            Toast.LENGTH_SHORT
                        ).show()
                        btnAddFollowOther.text = getString(R.string.cancel)
                        checkFollowOther(null)
                    }
                })
        }

    }

    private fun getInternship() {
        api.getInternship(MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : Callback<Internship> {
                override fun onFailure(call: Call<Internship>, t: Throwable) {
                    Log.e("getInternship", t.message)
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<Internship>, response: Response<Internship>) {
                    tvInternshipTitle.text = "Thực tập chuyên nghành đợt " +
                            response.body()?.internshipTerm?.term + " năm " + response.body()?.internshipTerm?.year
                    tvInternshipTime.text =
                        "Thời gian đăng ký thực tập: " + response.body()?.internshipTerm?.startDate +
                                " đến " + response.body()?.internshipTerm?.expiredDate

                    if (response.body()?.createdAt != null) {
                        btnInternship.text = getString(R.string.cancel)
                    }
                }

            })

    }


    private fun initFilterPartner() {
        val dialog = SimpleSearchDialogCompat(requireContext(), "Search...",
            "What are you looking for...?", null, dataFitResponse,
            SearchResultListener<Company> { dialog, item, position ->
                edtSearchPartner.setText(item.companyName.toString())
                companyId = item.id?.toInt()!!
                dialog.dismiss()
            }
        )

        edtSearchPartner.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                dialog.show()
                dialog.searchBox.typeface = Typeface.SERIF
                return false
            }

        })
    }

    private fun initFilterOther() {
        val dialog = SimpleSearchDialogCompat(requireContext(), "Search...",
            "What are you looking for...?", null, dataOtherRespone,
            SearchResultListener<Company> { dialog, item, position ->
                edtSearchOther.setText(item.companyName.toString())
                otherCompanyId = item.id?.toInt()!!
                dialog.dismiss()
            }
        )
        edtSearchOther.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                dialog.show()
                dialog.searchBox.typeface = Typeface.SERIF
                return false
            }

        })
    }


    private fun checkFollowOther(postTitle: String?) {
        val partnerDTO = PartnerDTO(0, null, null, null, postTitle, null)
        api.checkFollowId(
            "post/" + 0 + "/checkFollow",
            MySharedPreferences.getInstance(requireContext()).getToken(), partnerDTO
        )
            .enqueue(object : Callback<PartnerDTO> {
                override fun onFailure(call: Call<PartnerDTO>, t: Throwable) {
                    Log.e("checkfollow", t.message)
                }

                override fun onResponse(call: Call<PartnerDTO>, response: Response<PartnerDTO>) {
                    if (response.body()?.id != 0) {
                        edtSearchOther.visibility = View.GONE
                        btnFollowOther.visibility = View.GONE
                        edtOther1.setText(response.body()?.partner?.partnerName)
                        edtOther2.setText(response.body()?.partner?.address)
                        edtOther3.setText(response.body()?.partner?.email)
                        edtOther4.setText(response.body()?.partner?.phone)
                        edtOther1.isEnabled = false
                        edtOther2.isEnabled = false
                        edtOther3.isEnabled = false
                        edtOther4.isEnabled = false
                        btnAddFollowOther.text = getString(R.string.cancel)


                    }


                }
            })

    }

    private fun unfollowOther(id: Int?, postTitle: String?) {
        api.unfollow(
            "post/$id/student/unfollow",
            MySharedPreferences.getInstance(requireContext()).getToken(),
            PartnerDTO(null, null, null, null, postTitle, 0)
        ).enqueue(object : Callback<PartnerDTO> {
            override fun onFailure(call: Call<PartnerDTO>, t: Throwable) {
                Log.e("unfollow", t.message)
            }

            override fun onResponse(call: Call<PartnerDTO>, response: Response<PartnerDTO>) {
                edtOther1.setText("")
                edtOther2.setText("")
                edtOther3.setText("")
                edtOther4.setText("")
                btnAddFollowOther.setText(R.string.enrol)
                edtSearchOther.visibility = View.VISIBLE
                btnFollowOther.visibility = View.VISIBLE
                edtOther1.isEnabled = true
                edtOther2.isEnabled = true
                edtOther3.isEnabled = true
                edtOther4.isEnabled = true
                checkFollowFit()
                checkFollowOther("Recruitment_other")
                getInternship()


            }

        })
    }

    private fun unfollowPartner(id: Int?, postTitle: String?) {
        api.unfollow(
            "post/$id/student/unfollow",
            MySharedPreferences.getInstance(requireContext()).getToken(),
            PartnerDTO(null, null, null, null, postTitle, 0)
        ).enqueue(object : Callback<PartnerDTO> {
            override fun onFailure(call: Call<PartnerDTO>, t: Throwable) {
                Log.e("unfollow", t.message)
            }

            override fun onResponse(call: Call<PartnerDTO>, response: Response<PartnerDTO>) {
                edtSearchPartner.setText("")
                edtSearchPartner.isEnabled = true
                btnAddFollowPartner.text = getString(R.string.enrol)
                checkFollowFit()
                checkFollowOther("Recruitment_other")
                getInternship()
            }

        })
    }

    private fun checkFollowFit() {
        var partnerDTO = PartnerDTO(0, null, null, null, "Recruitment", null)
        api.checkFollowId(
            "post/" + 0 + "/checkFollow",
            MySharedPreferences.getInstance(requireContext()).getToken(), partnerDTO
        )
            .enqueue(object : Callback<PartnerDTO> {
                override fun onFailure(call: Call<PartnerDTO>, t: Throwable) {
                    Log.e("checkfollow", t.message)
                }

                override fun onResponse(call: Call<PartnerDTO>, response: Response<PartnerDTO>) {
                    if (response.body()?.id != 0) {
                        edtSearchPartner.setText(response.body()?.partner?.partnerName)
                        edtSearchPartner.isEnabled = false
                        btnAddFollowPartner.text = getString(R.string.cancel)


                    }
                }
            })
    }


    private fun initView() {
        dataMessage.observe(this, Observer {
            newMessageAdapter.setData(it)
        })
        dataPost.observe(this, Observer {
            postAdapter.setData(it)
        })
        initViewNewMessage()
        initViewPost()
        initViewOther()
    }

    private fun initViewOther() {
        btnAddFollowOther.setOnClickListener {
            if (btnAddFollowOther.text == getText(R.string.enrol)) {
                addOther()
            } else {
                unfollowOther(0, "Recruitment_other")
            }

        }
    }


    private fun getPost() {
        progressBar.visibility = View.VISIBLE

        api.getPost(0, 100, MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : Callback<Post> {
                override fun onFailure(call: Call<Post>, t: Throwable) {
                    Log.e("getpost", t.message)
                }

                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    progressBar.visibility = View.GONE

                    Log.e("codePost", response.code().toString())
                    response.body()?.let { body ->
                        lastPage = body.last
                        totalPage = body.totalPage
                        Log.e("page", lastPage.toString())
                        dataPostResponse.addAll(body.listContent)

                        dataPost.postValue(dataPostResponse.map { dataPostResponse ->
                            Content(
                                dataPostResponse.contentPost,
                                MySharedPreferences.getInstance(requireContext()).getDate(
                                    dataPostResponse.datePost.toLong()
                                ),
                                MySharedPreferences.getInstance(requireContext()).getDate(
                                    dataPostResponse.expiryTime.toLong()
                                ),
                                dataPostResponse.listFollows,
                                dataPostResponse.idPost,
                                dataPostResponse.partnerContact,
                                dataPostResponse.partnerName,
                                dataPostResponse.postType,
                                dataPostResponse.requiredNumber,
                                dataPostResponse.status,
                                dataPostResponse.title
                            )
                        } as ArrayList<Content?>)

                    }
                }
            })
    }

    private fun initViewPost() {
        btnShowPost.setOnClickListener {
            if (dataPostResponse.isEmpty()) {
                getPost()
            } else {
                if (recyclerPost.isGone) {
                    recyclerPost.visibility = View.VISIBLE
                } else {
                    recyclerPost.visibility = View.GONE
                }
            }

        }

    }

    override fun onPostClick(content: Content) {
        recyclerPost.visibility = View.VISIBLE
        var partnerDTO = PartnerDTO(content.idPost, null, null, null, null, null)
        api.checkFollowId(
            "post/" + content.idPost + "/checkFollow",
            MySharedPreferences.getInstance(requireContext()).getToken(), partnerDTO
        )
            .enqueue(object : Callback<PartnerDTO> {
                override fun onFailure(call: Call<PartnerDTO>, t: Throwable) {
                    Log.e("checkfollow", t.message)
                }

                override fun onResponse(call: Call<PartnerDTO>, response: Response<PartnerDTO>) {
                    val postDetailFragment = PostDetailFragment()
                    val bundle = Bundle()
                    var id = response.body()?.id
                    Log.e("postFollow", response.body()?.id.toString())
                    id?.let { bundle.putInt("id", it) }

                    bundle.putSerializable("object", content)

                    postDetailFragment.arguments = bundle

                    activity!!.supportFragmentManager.beginTransaction()
                        .replace(
                            (view!!.parent as ViewGroup).id,
                            postDetailFragment,
                            "findThisFragment"
                        )
                        .addToBackStack(null)
                        .commit()
                }
            })

    }


    private fun initViewNewMessage() {

        if (dataResponse.isEmpty()) {

            tvNoMessage.visibility = View.VISIBLE
        } else {
            tvNoMessage.visibility = View.GONE
        }

    }


    private fun getDataNewMessage() {
        Log.e("toantoken", MySharedPreferences.getInstance(requireContext()).getToken())
        api.getMessage(MySharedPreferences.getInstance(requireContext()).getToken())
            .enqueue(object : Callback<List<NewMessage>> {
                override fun onFailure(call: Call<List<NewMessage>>, t: Throwable) {
                    Log.e("toan", t.message.toString())

                }

                override fun onResponse(
                    call: Call<List<NewMessage>>,
                    response: Response<List<NewMessage>>
                ) {
                    response.body()?.let { body ->
                        dataResponse.addAll(body)

                        dataMessage.postValue(dataResponse.map { dataResponse ->
                            NewMessage(
                                dataResponse.content,
                                dataResponse.id,
                                dataResponse.lastUpdated,
                                dataResponse.messageType,
                                dataResponse.receiverName,
                                dataResponse.senderName,
                                MySharedPreferences.getInstance(requireContext()).getDate(
                                    dataResponse.sendDate.toLong()
                                ),
                                dataResponse.status,
                                dataResponse.title
                            )
                        } as ArrayList<NewMessage?>)

                    }
                }

            })
    }


    override fun onMessageClick(message: NewMessage) {
        message.id?.let { MySharedPreferences.getInstance(requireContext()).setIdMessage(it) }
        Log.e("id", MySharedPreferences.getInstance(requireContext()).getIdMessage())
        clickMessage()
        var builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(message.title)
        builder.setMessage(message.content.replace("<br />", "\n"))
        builder.setNeutralButton("Ok") { _, _ ->
            seenMessage()
            dataMessage.observe(this, Observer {
                it.remove(message)
                newMessageAdapter.notifyDataSetChanged()
            })

        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun seenMessage() {

        api.seenMessage(
            "message/" + MySharedPreferences.getInstance(requireContext()).getIdMessage() + "/seen",
            MySharedPreferences.getInstance(requireContext()).getToken()
        )
            .enqueue(object : Callback<NewMessage> {
                override fun onFailure(call: Call<NewMessage>, t: Throwable) {
                    Log.e("seen", t.message)
                }

                override fun onResponse(call: Call<NewMessage>, response: Response<NewMessage>) {
                    Log.e("codeSeen", response.code().toString())
                }

            })

    }

    private fun clickMessage() {
        api.clickMessage(
            "message/" + MySharedPreferences.getInstance(requireContext()).getIdMessage() + "/seen",
            MySharedPreferences.getInstance(requireContext()).getToken()
        )
            .enqueue(object : Callback<NewMessage> {
                override fun onFailure(call: Call<NewMessage>, t: Throwable) {
                    Log.e("Click", t.message)
                }

                override fun onResponse(call: Call<NewMessage>, response: Response<NewMessage>) {
                    Log.e("codeClickSeen", response.code().toString())
                }

            })

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)


    }


    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


}

